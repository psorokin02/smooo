<template>
  <SmoConfigurationInput :show-count="false" @filled="initSMO"/>

  <div class="info">
    Общее время работы системы: {{ totalStatistic.totalTime }}<br>
    Общее количество заявок: {{ totalStatistic.totalTasksCount }}<br>
    Вероятность отказа по всем заявкам: {{ totalStatistic.probabilityRejectForAllTasks }}<br>
    Среднее время завяки в системе: {{totalStatistic.averageTaskTimeInSystem}}<br>
    Средний коэф. использования приборов: {{totalStatistic.averageDeviceBusy}}
  </div>

  <br><br>
  <div class="producers">
    Источники
    <table class="table">
      <tr>
        <th>Номер источника</th>
        <th>Кол-во заявок</th>
        <th>P отказа</th>
        <th>Сред. преб</th>
        <th>Сред. обсл</th>
        <th>Сред. БП</th>
        <th>Дисперсия обсл</th>
        <th>Дисперсия БП</th>
      </tr>
      <tr v-for="producer in totalStatistic.producers" :key="producer.id">
        <td>{{ producer.id }}</td>
        <td>{{ producer.generatedTasksCount }}</td>
        <td>{{ producer.probabilityRejectTask }}</td>
        <td>{{ producer.averageTaskTimeInSystem }}</td>
        <td>{{ producer.averageTaskTimeHandling }}</td>
        <td>{{ producer.averageTaskTimeInBuffer }}</td>
        <td>{{ producer.dispersionAverageTaskTimeHandling }}</td>
        <td>{{ producer.dispersionAverageTaskTimeInBuffer }}</td>
      </tr>
    </table>
  </div>

  <br>
  <div class="devices">
    Приборы
    <table class="table">
      <tr>
        <th>Номер прибора</th>
        <th>Коэф. использования</th>
      </tr>
      <tr v-for="device in totalStatistic.devices" :key="device.id">
        <td>{{ device.id }}</td>
        <td>{{ device.coefficientOfBusy }}</td>
      </tr>
    </table>
  </div>

</template>

<script>
import * as totalApi from "@/api/ApiTotal"
import SmoConfigurationInput from "@/components/SmoConfigurationInput";

export default {
  name: "ResultsSmo",
  components: {
    SmoConfigurationInput
  },
  data() {
    return {
      totalStatistic: {}
    }
  },
  methods: {
    initSMO(config) {
      totalApi.getTotalStatistic(config)
          .then(it => this.totalStatistic = it, () => alert("Ошибка на сервере"))
    }
  }
}
</script>

<style scoped>
.table {
  /*width: 100%;*/
  margin-bottom: 20px;
  border: 1px solid #dddddd;
  border-collapse: collapse;
  max-width: 50%;
  position: relative;
  left: 30%;
  margin: 10px;
  top: 30%;
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

.info {
  position: relative;
  left: 30%;
  top: 50%;
  text-align: left;
}
</style>