<template>
  <div class="min-h-screen p-6 bg-gray-50">
    <div class="max-w-5xl mx-auto bg-white rounded-lg shadow p-6">
      <div class="flex items-center justify-between mb-4">
        <div>
          <h2 class="text-2xl font-semibold">Registered Users</h2>
          <div class="text-sm text-gray-500">Total: <span class="font-medium">{{ users.length }}</span></div>
        </div>

        <div class="flex items-center gap-3">
          <input v-model="search" type="text" placeholder="Search by name or email"
            class="border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400" />
          <button @click="load" class="px-3 py-2 border rounded">Refresh</button>
          <button @click="goToForm" class="px-4 py-2 bg-blue-500 text-white rounded">Add User</button>
        </div>
      </div>

      <div v-if="loading" class="text-gray-600">Loading users...</div>
      <div v-else-if="error" class="text-red-600 mb-4">{{ error }}</div>

      <div v-else>
        <table class="min-w-full bg-white shadow rounded">
          <thead class="bg-gray-100">
            <tr>
              <th class="py-2 px-4 text-left">Name</th>
              <th class="py-2 px-4 text-left">Email</th>
              <th class="py-2 px-4 text-left">Mobile</th>
              <th class="py-2 px-4 text-left">DOB</th>
              <th class="py-2 px-4 text-left">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="u in paginatedUsers" :key="u.id" class="border-t hover:bg-gray-50">
              <td class="py-2 px-4">{{ u.fullName }}</td>
              <td class="py-2 px-4">{{ u.email }}</td>
              <td class="py-2 px-4">{{ u.mobile }}</td>
              <td class="py-2 px-4">{{ formatDate(u.dob) }}</td>
              <td class="py-2 px-4">
                <button @click="del(u.id)" class="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>

        <div v-if="filteredUsers.length === 0" class="text-gray-600 mt-4">No users found.</div>

        <!-- Pagination -->
        <div class="mt-4 flex justify-center space-x-2">
          <button
            v-for="page in totalPages"
            :key="page"
            @click="currentPage = page"
            :class="['px-3 py-1 rounded', currentPage === page ? 'bg-blue-500 text-white' : 'bg-gray-200']"
          >
            {{ page }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import api from '../services/api'
import { useRouter } from 'vue-router'

const router = useRouter()
const users = ref([])
const search = ref('')
const currentPage = ref(1)
const perPage = 5
const loading = ref(false)
const error = ref('')

const SAMPLE_USERS = [
  {
    "id": "6981ca32ca5fad12406a81ce",
    "fullName": "Ram Kumar",
    "email": "Ramkumar@example.com",
    "mobile": "9876543210",
    "password": "Password@123",
    "dob": "1995-08-15"
  },
  {
    "id": "6981ca4cca5fad12406a81cf",
    "fullName": "Sai Kumar",
    "email": "saikumar@example.com",
    "mobile": "9876543210",
    "password": "Password@123",
    "dob": "1995-08-15"
  },
  {
    "id": "6981cb1dca5fad12406a81d0",
    "fullName": "Test user",
    "email": "test@example.com",
    "mobile": "9876543210",
    "password": "Password@123",
    "dob": "1995-08-15"
  }
]

const load = async () => {
  loading.value = true
  error.value = ''
  try {
    const res = await api.get('/users')
    // Expecting an array; if not, fallback to sample
    users.value = Array.isArray(res.data) ? res.data : SAMPLE_USERS
  } catch (err) {
    console.error(err)
    error.value = 'Unable to load users from API — showing sample data'
    users.value = SAMPLE_USERS
  } finally {
    loading.value = false
  }
}

const del = async (id) => {
  if (confirm('Are you sure you want to delete this user?')) {
    try {
      await api.delete(`/users/${id}`)
      await load()
    } catch (err) {
      console.error(err)
      alert('Failed to delete user')
    }
  }
}

const goToForm = () => router.push('/')

const formatDate = (d) => {
  if (!d) return '-'
  try {
    const dt = new Date(d)
    return dt.toLocaleDateString()
  } catch (e) {
    return d
  }
}

// Filtered users based on search
const filteredUsers = computed(() => {
  if (!search.value) return users.value
  return users.value.filter(u =>
    u.fullName.toLowerCase().includes(search.value.toLowerCase()) ||
    u.email.toLowerCase().includes(search.value.toLowerCase())
  )
})

// Pagination logic
const totalPages = computed(() => Math.max(1, Math.ceil(filteredUsers.value.length / perPage)))
const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * perPage
  return filteredUsers.value.slice(start, start + perPage)
})

onMounted(load)
</script>