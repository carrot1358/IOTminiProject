<script setup lang="ts">
import {onMounted, ref} from 'vue'
import axios from 'axios'
import {DeviceApi} from "~/Pages/Apiconfig";
import 'bootstrap/dist/css/bootstrap.css'


const sensorData = ref([])
const deviceData = ref([])


onMounted(() => {
  console.log('mounted')
  getDeviceData()
})

const addDevice = () => {
  navigateTo('/Device/addDevice')
}

const addSensor = () => {
  navigateTo('/Sensor/addSensor')

}

const getDeviceData = async () => {
  const response = await axios.get(`${DeviceApi}/getAllDevices`)
  console.log(response)
  deviceData.value = response.data.map(device => {
    return {id: device.id, name: device.name};
  })
  console.log(deviceData.value)
}

async function seeDetailDevice(deviceid: string, devicename: string) {
  await navigateTo(`/Device/DevicePage/?device_id=${deviceid}&device_name=${devicename}`)
}

</script>

<template>
  <div>
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
    <div class="card m-5">
      <div class="container-fluid text-center my-3">
        <button class="btn btn-primary mx-1" @click="addDevice">Add Device</button>
        <button class="btn btn-success mx-1" @click="addSensor">Add Sensor</button>

      </div>

      <div class="container text-center">
        <div class="row row-cols-2 row-cols-lg-5 g-2 g-lg-3" v-for="device in deviceData">
          <div class="container-fluid card p-3 my-3"
               style="box-shadow: rgba(0, 0, 0, 0.19) 0px 10px 20px, rgba(0, 0, 0, 0.23) 0px 6px 6px;">

            <h3>{{ device.name }}</h3>
            <button class="button-75 mx-5" @click="seeDetailDevice(device.id,device.name)" role="button"><span
                class="text">Detail</span></button>
          </div>
        </div>
      </div>
    </div>


  </div>

</template>

<style scoped>


/* CSS */
.button-75 {
  align-items: center;
  background-image: linear-gradient(135deg, #f34079 40%, #fc894d);
  border: 0;
  border-radius: 10px;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  font-family: "Codec cold", sans-serif;
  font-size: 16px;
  font-weight: 700;
  height: 54px;
  justify-content: center;
  letter-spacing: .4px;
  line-height: 1;
  max-width: 100%;
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 3px;
  text-decoration: none;
  text-transform: uppercase;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-75:active {
  outline: 0;
}

.button-75:hover {
  outline: 0;
}

.button-75 span {
  transition: all 200ms;
}

.button-75:hover span {
  transform: scale(.9);
  opacity: .75;
}

@media screen and (max-width: 991px) {
  .button-75 {
    font-size: 15px;
    height: 50px;
  }

  .button-75 span {
    line-height: 50px;
  }
}
</style>