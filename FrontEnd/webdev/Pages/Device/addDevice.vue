<script setup lang="ts">
import {onMounted, ref} from 'vue'
import axios from 'axios'
import {DeviceApi} from "~/Pages/Apiconfig";
import Swal from 'sweetalert2'


const deviceData = ref([])
const deviceName = ref('')

onMounted(() => {
  console.log('mounted')
})

const addDevice = async () => {
  const data = {
    name: deviceName.value

  }
  const response = await axios.post(`${DeviceApi}/Add_Devices`, data)
  if (response.data.status == 'Success') {
    await Swal.fire({
      icon: 'success',
      title: 'Device Added',
      text: `${response.data.message}`,
      timer: 2000
    })
    await navigateTo(`/sensor/addSensor/?device_id=${response.data.dataValue.id}`)
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

    <div class="container-fluid text-center w-50 mt-3">
      <h1>Add Device Page</h1>
      <div class="mt-3">
        <div class="input-group mb-3 ">
          <span class="input-group-text" id="basic-addon1">Device Name : </span>
          <input type="text" class="form-control" v-model="deviceName" placeholder="Name" aria-label="Name"
                 aria-describedby="basic-addon1">
        </div>
        <button class="button-82-pushable" @click="addDevice" role="button">
          <span class="button-82-shadow"></span>
          <span class="button-82-edge"></span>
          <span class="button-82-front text">
            Add Device
          </span>
        </button>
      </div>
    </div>

  </div>

</template>

<style scoped>

/* CSS */
.button-82-pushable {
  position: relative;
  border: none;
  background: transparent;
  padding: 0;
  cursor: pointer;
  outline-offset: 4px;
  transition: filter 250ms;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-82-shadow {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  background: hsl(0deg 0% 0% / 0.25);
  will-change: transform;
  transform: translateY(2px);
  transition: transform 600ms cubic-bezier(.3, .7, .4, 1);
}

.button-82-edge {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 12px;
  background: linear-gradient(
      to left,
      hsl(340deg 100% 16%) 0%,
      hsl(340deg 100% 32%) 8%,
      hsl(340deg 100% 32%) 92%,
      hsl(340deg 100% 16%) 100%
  );
}

.button-82-front {
  display: block;
  position: relative;
  padding: 12px 27px;
  border-radius: 12px;
  font-size: 1.1rem;
  color: white;
  background: hsl(345deg 100% 47%);
  will-change: transform;
  transform: translateY(-4px);
  transition: transform 600ms cubic-bezier(.3, .7, .4, 1);
}

@media (min-width: 768px) {
  .button-82-front {
    font-size: 1.25rem;
    padding: 12px 42px;
  }
}

.button-82-pushable:hover {
  filter: brightness(110%);
  -webkit-filter: brightness(110%);
}

.button-82-pushable:hover .button-82-front {
  transform: translateY(-6px);
  transition: transform 250ms cubic-bezier(.3, .7, .4, 1.5);
}

.button-82-pushable:active .button-82-front {
  transform: translateY(-2px);
  transition: transform 34ms;
}

.button-82-pushable:hover .button-82-shadow {
  transform: translateY(4px);
  transition: transform 250ms cubic-bezier(.3, .7, .4, 1.5);
}

.button-82-pushable:active .button-82-shadow {
  transform: translateY(1px);
  transition: transform 34ms;
}

.button-82-pushable:focus:not(:focus-visible) {
  outline: none;
}
</style>