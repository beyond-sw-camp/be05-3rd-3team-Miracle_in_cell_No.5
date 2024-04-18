<template>
  <div class="container custom-container">
    <div class="d-flex justify-content-between align-items-center mt-5">
      <h3>확성기 예약</h3>
      <a class="btn btn-secondary" href="/">←</a>
    </div>
    <hr>
    <reservation-form></reservation-form>
  </div>
</template>

<script>
import ReservationForm from "@/components/ReservationForm.vue";

export default {
  components: {
    ReservationForm
  }
}
</script>

<style scoped>
.custom-container {
  max-width: 800px;
  overflow-y: auto;
}

.field-error {
  border-color: #dc3545;
  color: #dc3545;
}
</style>
