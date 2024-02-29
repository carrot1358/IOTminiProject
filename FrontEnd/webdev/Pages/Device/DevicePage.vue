<script setup lang="ts">
import {onMounted, ref} from 'vue'
import axios from 'axios'
import {SensorApi} from "~/Pages/Apiconfig";
import Swal from 'sweetalert2'

const device_id = ref('')
const devicename = ref('')
const SensorData = ref([])


onMounted(() => {
  console.log('mounted')
  const url = new URL(window.location.href)
  const searchParams = new URLSearchParams(url.search)
  device_id.value = searchParams.get('device_id') || ''
  devicename.value = searchParams.get('device_name') || ''

  getSensorData()

  setInterval(() => {
    getSensorData();
  }, 1000);

})

const goToaddSensor = () => {
  navigateTo(`/Sensor/addSensor/?device_id=${device_id.value}`)
}

const getSensorData = async () => {
  const res = await axios.get(`${SensorApi}/getSensorInfoByDeviceId/${device_id.value}`)
  SensorData.value = res.data
  console.log(res)
}
const See_UUID = async () => {
  await Swal.fire({
    icon: 'success',
    title: 'Device UUID',
    text: `UUID : ${device_id.value}`,
  })
}

</script>

<template>


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

  <div class="card container-fluid text-center py-3 justify-center d-flex w-50 content-center align-items-center mt-3"
       style="box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;">
    <div>
      <h1> Device {{ devicename }}</h1>
      <button @click="goToaddSensor" class="btn btn-primary mx-2" role="button">Add Sensor</button>
      <button @click="See_UUID" class="btn btn-warning mx-2" role="button">See UUID</button>
    </div>
    <div v-for="i in SensorData " class="d-flex card my-2 py-1 px-4 w-auto text-center items-center "
         style="box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;">
      <div>
        <h3>Name : {{ i.name }}</h3>
        <!-- <h3>Pin : {{ i.pin }}</h3>-->
        <h3>Value : {{ i.value }}</h3>
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>