<!--<template>-->
<!--  <form @submit.prevent="submitReservation">-->
<!--    <div class="form-group">-->
<!--      <label for="reservationDate"><strong>예약 날짜: </strong></label>-->
<!--      <div class="input-group">-->
<!--        <input type="date" v-model="reservationDate" class="form-control" required>-->
<!--        <div class="input-group-append">-->
<!--          <span class="input-group-text"><i class="fa fa-calendar"></i></span>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

<!--    <div class="form-group">-->
<!--      <label for="reservationTime"><strong>예약 시간: </strong></label>-->
<!--      <div class="input-group">-->
<!--        <select v-model="reservationTime" class="custom-select" required>-->
<!--          <option value="" disabled selected>Select a time</option>-->
<!--          <option v-for="time in availableTimes" :key="time" :value="time">{{ time }}</option>-->
<!--        </select>-->
<!--        <div class="input-group-append">-->
<!--          <span class="input-group-text"><i class="fa fa-clock-o"></i></span>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

<!--    <div class="form-group">-->
<!--      <label for="content"><strong>내용</strong></label>-->
<!--      <textarea v-model="content" class="form-control form-floating mb-3" id="content" rows="6"-->
<!--                placeholder="확성기 내용을 작성하세요." required></textarea>-->
<!--    </div>-->

<!--    <button type="submit" class="btn btn-primary">확성기 예약</button>-->

<!--    <div v-if="hasGlobalErrors">-->
<!--      <p class="field-error" v-for="error in globalErrors" :key="error">{{ error }}</p>-->
<!--    </div>-->
<!--  </form>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  setup() {-->

<!--    function setMinDate() {-->
<!--      const today = new Date().toISOString().split('T')[0];-->
<!--      document.getElementById("reservationDate").setAttribute("min", today);-->
<!--    }-->

<!--    // Function to update time options based on selected date and existing reservations-->
<!--    function updateTimeOptions(selectedDate, reservedTimes) {-->
<!--      const select = document.getElementById("reservationTime");-->
<!--      const now = new Date();-->
<!--      const currentHour = now.getHours();-->
<!--      const currentMinute = now.getMinutes();-->

<!--      select.innerHTML = '';-->

<!--      const selectedDateObj = new Date(selectedDate);-->

<!--      const isAfterToday = selectedDateObj > now;-->

<!--      // Add options for future times not in the reservedTimes array if the selected date is today or after-->
<!--      for (let hour = isAfterToday ? 0 : currentHour; hour <= 23; hour++) {-->
<!--        for (let minute = 0; minute < 60; minute += 30) {-->
<!--          const timeString = (hour < 10 ? '0' : '') + hour + ':' + (minute === 0 ? '00' : minute);-->
<!--          const option = document.createElement('option');-->
<!--          option.value = timeString;-->
<!--          option.textContent = timeString;-->
<!--          if (!reservedTimes.includes(timeString) && (isAfterToday || (hour > currentHour || (hour === currentHour && minute >= currentMinute)))) {-->
<!--            select.appendChild(option);-->
<!--          }-->
<!--        }-->
<!--      }-->
<!--    }-->

<!--    // Function to handle date change and update time options-->
<!--    function handleDateChange(selectedDate) {-->
<!--      // Retrieve reserved times from the server based on the selected date (if needed)-->
<!--      const reservedSpeakersTime = [[${reservedSpeakersTime}]]; // Example reserved times, replace with actual data-->

<!--      // Extract times for the selected date-->
<!--      const selectedDateTimes = reservedSpeakersTime.filter(time => time.startsWith(selectedDate));-->

<!--      // Extract only hours and minutes from the selectedDateTimes-->
<!--      const reservedTimes = selectedDateTimes.map(time => {-->
<!--        const dateObj = new Date(time);-->
<!--        const hour = dateObj.getHours();-->
<!--        const minute = dateObj.getMinutes();-->
<!--        return (hour < 10 ? '0' : '') + hour + ':' + (minute < 10 ? '0' : '') + minute;-->
<!--      });-->

<!--      updateTimeOptions(selectedDate, reservedTimes);-->
<!--    }-->

<!--    return {}-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.custom-container {-->
<!--  max-width: 600px;-->
<!--  overflow-y: auto;-->
<!--  margin: auto;-->
<!--  padding: 20px;-->
<!--}-->

<!--.reservation-btn {-->
<!--  background-color: #5F73B0;-->
<!--  color: white;-->

<!--  &:hover {-->
<!--    background-color: white;-->
<!--    color: black;-->
<!--  }-->
<!--}-->

<!--.fixed-size-textarea {-->
<!--  resize: none; /* 리사이즈 비활성화 */-->
<!--  width: 70%; /* 원하는 너비로 지정 */-->
<!--  height: 150px; /* 원하는 높이로 지정 */-->
<!--}-->

<!--.arrow-btn {-->
<!--  width: 35px;-->
<!--  height: 35px;-->
<!--  cursor: pointer; /* 여기서 커서 모양을 변경합니다 */-->
<!--}-->
<!--</style>-->