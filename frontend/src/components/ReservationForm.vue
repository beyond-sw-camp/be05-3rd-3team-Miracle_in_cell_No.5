<template>
  <form @submit.prevent="submitReservation">
    <label for="reservationDate"><strong>예약 날짜: </strong></label>
    <div class="input-group">
      <input type="date" min="" id="reservationDate"
             v-model="reservationDate" class="form-control" required @change="handleDateChange">
    </div>

    <label for="reservationTime"><strong>예약 시간: </strong></label>
    <div class="input-group">
      <select id="reservationTime" class="form-control" required>
        <option value="" disabled selected>Select a time</option>
      </select>
    </div>

    <div class="form-group">
      <label for="content"><strong>내용</strong></label>
      <textarea class="form-control form-floating mb-3" id="content" rows="6" placeholder="확성기 내용을 작성하세요."
                required v-model="content"></textarea>
    </div>

    <button type="submit" class="btn btn-primary">확성기 예약</button>

    <div v-if="hasGlobalErrors">
      <p class="field-error" v-for="error in globalErrors" :key="error">{{ error }}</p>
    </div>
  </form>
  <hr>
</template>

<script>
import speakerApi from '@/apis/speaker';
import {computed, onMounted, ref} from 'vue';
import {useRouter} from "vue-router";

export default {
  setup() {
    const globalErrors = ref([]);
    const reservationDate = ref('');
    const content = ref('');
    const router = useRouter();

    const handleDateChange = async (event) => {
      const selectedDate = event.target.value;
      const today = new Date().toISOString().split('T')[0];
      if (selectedDate < today) {
        globalErrors.value.push('오늘보다 이전 날짜는 선택할 수 없습니다.');
        return;
      }
      // 서버에서 선택된 날짜에 대한 예약 가능한 시간을 가져오는 로직 추가
      try {
        let obj = await speakerApi.getFindSpeaker(selectedDate);
        let reservedTimes = [];
        for (let i = 0; i < obj.data.data.length; i++) {
          let items = obj.data.data[i].reservationDateTime;
          let time = items.split('T')[1].substring(0, 5);
          reservedTimes.push(time);
        }
        console.log(reservedTimes);
        updateTimeOptions(selectedDate, reservedTimes);
      } catch (error) {
        globalErrors.value.push('예약 가능한 시간을 가져오는 중 오류가 발생했습니다.');
      }
    };

    function updateTimeOptions(selectedDate, reservedTimes) {
      const select = document.getElementById('reservationTime');
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
    }

    const hasGlobalErrors = computed(() => globalErrors.value.length > 0);

    const setMinDate = () => {
      const today = new Date().toISOString().split('T')[0];
      document.getElementById('reservationDate').setAttribute('min', today);
    };

    const submitReservation = async () => {
      // 예약 정보를 서버로 전송하는 로직 추가
      globalErrors.value = [];
      const response = await speakerApi.postSaveSpeaker({
        reservationDate: reservationDate.value,
        reservationTime: document.getElementById('reservationTime').value,
        content: content.value
      });

      if (response.data.isSuccess) {
        alert('확성기 예약이 완료되었습니다.');
        router.push('/');
      } else {
        globalErrors.value.push(response.data.message);
      }
    };

    onMounted(() => {
      setMinDate();
    });

    return {
      globalErrors,
      reservationDate,
      handleDateChange,
      hasGlobalErrors,
      submitReservation,
      content
    };
  }
};
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
