<template>
  <div class="min-h-screen p-6 bg-gray-100">
    <div class="max-w-4xl mx-auto bg-white rounded-lg shadow p-6 grid grid-cols-1 md:grid-cols-3 gap-6">

      <!-- Left panel (info / quick actions) -->
      <aside class="md:col-span-1 flex flex-col items-center justify-center border-r pr-6">
        <div class="w-24 h-24 bg-gray-200 rounded-full mb-4" aria-hidden></div>
        <h2 class="text-xl font-semibold">New User</h2>
        <p class="text-sm text-gray-500 mt-2 text-center">Enter user details to register. Use the button to switch to the Users table.</p>
        <button @click="goToUsers" class="mt-4 px-4 py-2 bg-blue-500 text-white rounded">View Users</button>
      </aside>

      <!-- Form area -->
      <div class="md:col-span-2">
        <form @submit.prevent="submit" class="grid grid-cols-1 md:grid-cols-2 gap-4" novalidate>

          <div class="col-span-2 md:col-span-2">
            <label class="block text-gray-600 font-medium mb-2" for="fullName">Full Name</label>
            <input id="fullName" v-model="user.fullName" type="text" placeholder="John Doe" required
              class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 transition"
            />
            <p v-if="errors.fullName" class="text-red-500 text-sm mt-1">{{ errors.fullName }}</p>
          </div>

          <div>
            <label class="block text-gray-600 font-medium mb-2" for="email">Email</label>
            <input id="email" v-model="user.email" type="email" placeholder="you@example.com" required
              class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 transition"
            />
            <p v-if="errors.email" class="text-red-500 text-sm mt-1">{{ errors.email }}</p>
          </div>

          <div>
            <label class="block text-gray-600 font-medium mb-2" for="mobile">Mobile</label>
            <input id="mobile" v-model="user.mobile" type="tel" placeholder="+91 98765 43210" required
              class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 transition"
            />
            <p v-if="errors.mobile" class="text-red-500 text-sm mt-1">{{ errors.mobile }}</p>
          </div>

          <div>
            <label class="block text-gray-600 font-medium mb-2" for="password">Password</label>
            <input id="password" v-model="user.password" type="password" placeholder="Enter your password" required minlength="6"
              class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 transition"
            />
            <p v-if="errors.password" class="text-red-500 text-sm mt-1">{{ errors.password }}</p>
          </div>

          <div class="col-span-2 md:col-span-1">
            <label class="block text-gray-600 font-medium mb-2" for="dob">Date of Birth</label>
            <input id="dob" v-model="user.dob" type="date"
              class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-blue-400 transition"
            />
          </div>

          <!-- Actions (span full width) -->
          <div class="col-span-2 flex items-center justify-end gap-3 mt-2">
            <button type="button" @click="goToUsers" class="px-4 py-3 border border-gray-300 rounded-xl text-gray-700 hover:bg-gray-50">View Users</button>
            <button type="submit" :disabled="submitting" class="px-6 py-3 bg-blue-500 text-white rounded-xl shadow disabled:opacity-50">
              <span v-if="!submitting">Register</span>
              <span v-else>Registering...</span>
            </button>
          </div>

        </form>

        <div v-if="success" class="text-green-600 mt-4">🎉 User registered successfully!</div>
        <div v-if="errorMessage" class="text-red-600 mt-3">{{ errorMessage }}</div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../services/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const success = ref(false)
const submitting = ref(false)
const errorMessage = ref('')

const user = reactive({
  fullName: '',
  email: '',
  mobile: '',
  password: '',
  dob: ''
})

const errors = reactive({
  fullName: '',
  email: '',
  mobile: '',
  password: ''
})

// Simple email regex
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

const validate = () => {
  let valid = true
  errors.fullName = user.fullName ? '' : 'Full Name is required'
  if (errors.fullName) valid = false

  if (!user.email) errors.email = 'Email is required'
  else if (!emailRegex.test(user.email)) errors.email = 'Invalid email'
  else errors.email = ''
  if (errors.email) valid = false

  const mobileRaw = user.mobile ? user.mobile.trim() : ''
  if (!mobileRaw) {
    errors.mobile = 'Mobile number is required'
    valid = false
  } else {
    const digits = mobileRaw.replace(/\D/g, '')
    if (digits.length < 10) {
      errors.mobile = 'Mobile number must have at least 10 digits'
      valid = false
    } else if (digits.length > 15) {
      errors.mobile = 'Mobile number looks too long'
      valid = false
    } else {
      errors.mobile = ''
    }
  }

  if (!user.password) errors.password = 'Password is required'
  else if (user.password.length < 6) errors.password = 'Password must be at least 6 characters'
  else errors.password = ''
  if (errors.password) valid = false

  return valid
}

const submit = async () => {
  errorMessage.value = ''
  if (!validate()) return
  submitting.value = true
  try {
    await api.post('/users', { ...user })
    success.value = true
    // reset form after success
    user.fullName = ''
    user.email = ''
    user.mobile = ''
    user.password = ''
    user.dob = ''
    setTimeout(() => router.push('/users'), 1000)
  } catch (err) {
    console.error(err)
    errorMessage.value = err?.response?.data?.message || 'Error registering user. Please try again.'
  } finally {
    submitting.value = false
  }
}

const goToUsers = () => router.push('/users')
</script>
