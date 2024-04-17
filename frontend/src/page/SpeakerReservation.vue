<template>
  <div class="container custom-container">

    <div class="d-flex justify-content-between align-items-center mt-5">
        <h3>확성기 예약</h3> <!-- Back button added here -->
        <!-- <div class="btn btn-secondary" @click="()=>{this.$router.go(-1)}">←</div> -->
        <div @click="()=>{this.$router.go(-1)}"><img src='img/arrow-left.png' class='arrow-btn'/></div>
    </div>
    <hr>

    <form method="post">

        <div class="form-group">
            <label for="reservationDate"><strong>예약 날짜: </strong></label>
            <div class="input-group">
                <input type="date" id="reservationDate" class="form-control" required>
                <!-- <div class="input-group-append">
                    <span class="input-group-text"><i class="fa fa-calendar"></i></span>
                </div> -->
            </div>
        </div>


        <div class="form-group">
            <label for="reservationTime"><strong>예약 시간: </strong></label>
            <div class="input-group">
                <select id="reservationTime" class="custom-select" required>
                    <option value="" disabled selected>Select a time</option>
                </select>
                <!-- <div class="input-group-append">
                    <span class="input-group-text"><i class="fa fa-clock-o"></i></span>
                </div> -->
            </div>
        </div>

        <div class="form-group">
            <label for="content"><strong>내용</strong></label>
            <textarea class="form-control form-floating mb-3 fixed-size-textarea" id="content" rows="2"
                      placeholder="확성기 내용을 작성하세요." required></textarea>
        </div>

        <button type="submit" class="btn reservation-btn">확성기 예약</button>

        <!-- <div th:if="${#fields.hasGlobalErrors()}">
            <p class="field-error" th:each="err : ${#fields.globalErrors()}"
               th:text="${err}">전체 오류 메시지</p>
        </div> -->
    </form>
</div>
</template>

<script>
export default {

}
</script>

<style scoped>
.custom-container {
    max-width: 600px;
    overflow-y: auto;
    margin: auto;
    padding: 20px;
}
.reservation-btn{
    background-color: #5F73B0;
    color: white;
    &:hover{
        background-color: white;
        color: black;
    }
}
.fixed-size-textarea {
  resize: none; /* 리사이즈 비활성화 */
  width: 70%; /* 원하는 너비로 지정 */
  height: 150px; /* 원하는 높이로 지정 */
}
.arrow-btn{
    width: 35px;
    height: 35px;
    cursor: pointer; /* 여기서 커서 모양을 변경합니다 */
}
</style>