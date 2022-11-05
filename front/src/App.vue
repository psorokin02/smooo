<template>
  <button class="btn1" @click="showStep=true">step by step</button>
  <button class="btn2" @click="showStep=false">total statistic</button>
  <step-by-step v-if="showStep"></step-by-step>
  <total-smo v-if="!showStep"></total-smo>
</template>

<script>
import StepByStep from "@/components/StepByStep";
import TotalSmo from "@/components/TotalSmo";

export default {
  name: 'App',
  components: {
    StepByStep,
    TotalSmo
  },
  data() {
    return {
      showStep: true
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.btn1 {
  margin: 15px;
  min-height: 15px;
  min-width: 15px;
  padding: 10px;
  position: absolute;
  left: 5%;
  display: flex;
  top: 0;
}
.btn2 {
  margin: 15px;
  min-height: 15px;
  min-width: 15px;
  padding: 10px;
  position: absolute;
  left: 12%;
  display: flex;
  top: 0;
}
</style>
