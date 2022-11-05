<template>
  <div class="input-config">
    <div > Количество источников:</div>
    <input class="input" type="number" v-model="config.countProducers"> <br><br>

    <div class="text">Количество приборов:</div>
    <input class="input" type="number" v-model="config.countDevices"> <br><br>

    <div class="text">Размер буффера:</div>
    <input class="input" type="number" v-model="config.bufferSize"> <br><br>

    <div class="text">Время обработки заявки прибором (нижняя граница):</div>
    <input class="input" type="number" v-model="config.taskProcessDeviceTimeLowerBound"> <br><br>

    <div class="text">Время обработки заявки прибором (верхняя граница):</div>
    <input class="input" type="number" v-model="config.taskProcessDeviceTimeUpperBound"> <br><br>

    <div class="text">Среднее количество заявок от источника в секунду:</div>
    <input class="input" type="number" v-model="config.producerIntensity"> <br><br>

    <div v-if="showCount" class="text">Общее кол-во заявок:</div>
    <input v-if="showCount" class="input" type="number" v-model="config.totalCountTasks"> <br><br>

    <button class="input" @click="configFilled">Задать настройки</button>
  </div>
</template>

<script>
export default {
  name: "SmoConfigurationInput",
  props: {
    showCount: {
    }
  },
  data() {
    return {
      config: {}
    }
  },
  methods: {
    configFilled() {
      this.$emit("filled", this.config)
    }
  }
}
</script>

<style scoped>
.input-config {
  border: 1px solid black;
  border-radius: 5px;
  position: absolute;
  padding: 10px;

  display: block;
  text-align: left;
  top: 15%;
}
.input {
  /*padding: 10px;*/
  /*position: absolute;*/
  /*margin: 5px;*/
  display: block;
}
.text {
  /*padding: 10px;*/
  /*position: absolute;*/
  /*margin: 10px;*/
  display: block;
}
</style>