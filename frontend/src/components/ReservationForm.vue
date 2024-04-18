<template onload="setMinDate">
  <form @submit.prevent="submitReservation">
    <div class="form-group">
      <label for="reservationDate"><strong>예약 날짜: </strong></label>
      <div class="input-group">
        <input type="date" v-model="reservationDate" class="form-control" required
               v-on:change="handleDateChange(this.$event)">
        <div class="input-group-append">
          <span class="input-group-text"><i class="fa fa-calendar"></i></span>
        </div>
      </div>
    </div>

    <div class="form-group">
      <label for="reservationTime"><strong>예약 시간: </strong></label>
      <div class="input-group">
        <select v-model="reservationTime" class="custom-select" required>
          <option value="" disabled selected>Select a time</option>
          <option v-for="time in availableTimes" :key="time" :value="time">{{ time }}</option>
        </select>
        <div class="input-group-append">
          <span class="input-group-text"><i class="fa fa-clock-o"></i></span>
        </div>
      </div>
    </div>

    <div class="form-group">
      <label for="content"><strong>내용</strong></label>
      <textarea v-model="content" class="form-control form-floating mb-3" id="content" rows="6"
                placeholder="확성기 내용을 작성하세요." required></textarea>
    </div>

    <button type="submit" class="btn btn-primary">확성기 예약</button>

    <div v-if="hasGlobalErrors">
      <p class="field-error" v-for="error in globalErrors" :key="error">{{ error }}</p>
    </div>
  </form>
</template>

<script>
import speakerApi from '@/apis/speaker';

export default {
  data() {
    return {
      globalErrors: [],
    };
  },
  methods: {
    setMinDate() {
      const today = new Date().toISOString().split('T')[0];
      this.$refs.reservationDate.min = today;
    },
    handleDateChange(event) {
      const selectedDate = event.target.value;
      // 여기에는 서버에서 선택된 날짜에 대한 예약 가능한 시간을 가져오는 로직을 추가할 수 있습니다.
      let reservedTimes = speakerApi.getFindSpeaker(selectedDate);
      console.log(reservedTimes);
      this.updateTimeOptions(selectedDate, reservedTimes);
    },
    submitReservation() {
      // 여기에는 예약을 제출하는 로직을 추가할 수 있습니다.
      // 데이터를 유효성 검사하고 서버로 전송할 수 있습니다.

    },
    updateTimeOptions(selectedDate, reservedTimes) {
      const select = this.$refs.reservationTime;
      const now = new Date();
      const currentHour = now.getHours();
      const currentMinute = now.getMinutes();

      select.innerHTML = '';

      const selectedDateObj = new Date(selectedDate);
      const isAfterToday = selectedDateObj > now;

      for (let hour = isAfterToday ? 0 : currentHour; hour <= 23; hour++) {
        for (let minute = 0; minute < 60; minute += 30) {
          const timeString = `${hour < 10 ? '0' : ''}${hour}:${minute === 0 ? '00' : minute}`;
          const option = document.createElement('option');
          option.value = timeString;
          option.textContent = timeString;
          if (!reservedTimes.includes(timeString) && (isAfterToday || (hour > currentHour || (hour === currentHour && minute >= currentMinute)))) {
            select.appendChild(option);
          }
        }
      }
    },
  },
  computed: {
    hasGlobalErrors() {
      return this.globalErrors.length > 0;
    },
  },
}
</script>


<style scoped>
.custom-container {
  max-width: 600px;
  overflow-y: auto;
  margin: auto;
  padding: 20px;
}

.field-error {
  border-color: #dc3545;
  color: #dc3545;
}
</style>
