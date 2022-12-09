<template>
  <SmoConfigurationInput :show-count="true" @filled="initSMO"/>
  <div class="smo-stat">
    <button class="step-btn" @click="makeStep">Make step</button>

    <div class="producers">
      Источники
      <table class="table">
        <tr><th>Номер источника</th><th>Время следующей заявки</th></tr>
        <tr v-for="producer in smoStatistics.producers" :key="producer.id">
          <td>{{producer.id}}</td><td>{{producer.nextTaskTime}}</td>
        </tr>
      </table>
    </div>

    <div class="devices">
      Приборы
      <table class="table">
        <tr>
          <th>Номер прибора</th>
          <th>Время освобождения</th>
          <th>Номер источника текущей завяки</th>
        </tr>
        <tr v-for="device in smoStatistics.devices" :key="device.id">
          <td>{{device.id}}</td>
          <td v-if="device.nextFreeTime !== -1">{{device.nextFreeTime}}</td>
          <td v-else>свободен</td>
          <td v-if="device.currentTask">{{device.currentTask.producerId}}</td>
          <td v-else>нет заявки</td>
        </tr>
      </table>
    </div>

    <div class="buffer">
      Буффер
      <table class="table">
        <tr>
          <th>Номер источника</th>
          <th>Время создания</th>
        </tr>
        <tr v-for="task in smoStatistics.queue" :key="task.producerId + task.createdTime">
          <td>{{task.producerId}}</td>
          <td>{{task.createdTime}}</td>
        </tr>
      </table>
    </div>

  </div>
</template>

<script>
import SmoConfigurationInput from "@/components/SmoConfigurationInput";
import * as stepApi from "@/api/ApiStep"

export default {
  name: "StepByStep",
  components: {
    SmoConfigurationInput
  },
  data() {
    return {
      smoStatistics: {}
    }
  },
  methods: {
    initSMO(config) {
      stepApi.initStepSMO(config)
          .then(it => this.smoStatistics = it)
    },
    makeStep() {
      stepApi.makeStep()
          .then(it => this.smoStatistics = it, () => alert("Задайте конфигурацию"))
          .then(() => {
            if(this.smoStatistics.isEnded) alert("Шаги закончились")
          })
    }
  }
}
</script>

<style scoped>
.smo-stat {
  position: absolute;
  left: 30%;
}
.producers {
  display: table;
  position: relative;
}
.devices {

}
.table {
  width: 100%;
  margin-bottom: 20px;
  border: 1px solid #dddddd;
  border-collapse: collapse;
}
.table th {
  font-weight: bold;
  padding: 5px;
  background: #efefef;
  border: 1px solid #dddddd;
}
.table td {
  border: 1px solid #dddddd;
  padding: 5px;
}
.step-btn {
  position: absolute;
  right: -20%;
  min-width: 20px;
  min-height: 20px;
  color: blue;
}
</style>