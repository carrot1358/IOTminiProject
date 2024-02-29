<script setup lang="ts">

import {ref, onMounted} from 'vue'
import axios from 'axios'
import {SensorApi, DeviceApi} from "~/Pages/Apiconfig";
import Swal from 'sweetalert2'

const deviceData = ref([])
const sensorName = ref('')
const device_id = ref('')
const pin = ref('')

onMounted(() => {
  console.log('mounted')
  const url = new URL(window.location.href)
  const searchParams = new URLSearchParams(url.search)
  device_id.value = searchParams.get('device_id') || ''
  console.log(device_id.value)
  getDeviceData()
})

const getDeviceData = async () => {
  const response = await axios.get(`${DeviceApi}/getAllDevices`)
  console.log(response)
  deviceData.value = response.data.map(device => {
    return {id: device.id, name: device.name};
  })
  console.log(deviceData.value)
}
const addSensor = async () => {
  const data = {
    name: sensorName.value,
    pin: pin.value,
    deviceId: device_id.value
  }
  const response = await axios.post(`${SensorApi}/Add_Sensors`, data)
  if (response.data.status == 'Success') {
    await Swal.fire({
      icon: 'success',
      title: 'Sensor Added',
      text: `${response.data.message}`,
      timer: 2000
    })
    await navigateTo(`/sensor/addSensor/?device_id=${device_id.value}`)
  } else if (response.data.status == 'Failed') {
    await Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: `${response.data.message}`,
      timer: 2000
    })
  }
  console.log(response)
}
</script>

<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="/">Navbar</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="/">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/Device/addDevice">Add Device</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/Sensor/addSensor">Add Sensor</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

    <div class="card mx-5 p-5 mt-3" style="box-shadow: rgba(0, 0, 0, 0.25) 0px 25px 50px -12px;">
      <div class="container-fluid text-center">
        <h1>Add Sensor Page</h1>
        <div class="w-50 container-fluid text-center ">
          <div class="d-flex justify-content-between w-100">
            <div class="mb-3 mr-1">
              <select name="device_id" id="device_id" v-model="device_id" class="form-select"
                      aria-label="Default select example">
                <option v-for="device in deviceData" :value="device.id">{{ device.name }}</option>
              </select>
            </div>
            <div class="input-group mb-3 mx-1" >
              <span class="input-group-text" id="basic-addon1">Device ID</span>
              <input type="text" v-model="device_id" class="form-control" placeholder="Enter Device ID"
                     aria-label="Device Id"
                     aria-describedby="basic-addon1">
            </div>
          </div>

          <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">sensorName</span>
            <input type="text" v-model="sensorName" class="form-control" placeholder="sensorName"
                   aria-label="sensorName"
                   aria-describedby="basic-addon1">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon1">pin</span>
            <input type="text" v-model="pin" class="form-control" placeholder="pin" aria-label="pin"
                   aria-describedby="basic-addon1">
          </div>
          <button @click="addSensor" class="button-83" role="button">Add Sensor</button>

        </div>
      </div>
    </div>


  </div>
</template>

<style scoped>


.container-fluid.text-center {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

/* CSS */
.button-83 {
  appearance: button;
  background-color: transparent;
  background-image: linear-gradient(to bottom, #fff, #f8eedb);
  border: 0 solid #e5e7eb;
  border-radius: .5rem;
  box-sizing: border-box;
  color: #482307;
  column-gap: 1rem;
  cursor: pointer;
  display: flex;
  font-family: ui-sans-serif, system-ui, -apple-system, system-ui, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
  font-size: 100%;
  font-weight: 700;
  line-height: 24px;
  margin: 0;
  outline: 2px solid transparent;
  padding: 1rem 1.5rem;
  text-align: center;
  text-transform: none;
  transition: all .1s cubic-bezier(.4, 0, .2, 1);
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  box-shadow: -6px 8px 10px rgba(81, 41, 10, 0.1), 0px 2px 2px rgba(81, 41, 10, 0.2);
}

.button-83:active {
  background-color: #f3f4f6;
  box-shadow: -1px 2px 5px rgba(81, 41, 10, 0.15), 0px 1px 1px rgba(81, 41, 10, 0.15);
  transform: translateY(0.125rem);
}

.button-83:focus {
  box-shadow: rgba(72, 35, 7, .46) 0 0 0 4px, -6px 8px 10px rgba(81, 41, 10, 0.1), 0px 2px 2px rgba(81, 41, 10, 0.2);
}
</style>