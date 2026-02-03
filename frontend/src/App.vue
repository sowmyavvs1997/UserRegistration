<template>
  <div class="min-h-screen flex bg-gray-100">
    <!-- Sidebar -->
    <aside class="bg-white border-r p-6 hidden md:block">
      <div class="text-2xl font-bold mb-6">User Management Portal</div>
      <nav class="flex flex-col space-y-2">
        <router-link to="/" class="px-4 py-2 rounded hover:bg-gray-100" active-class="bg-gray-100 font-semibold">Register</router-link>
        <router-link to="/users" class="px-4 py-2 rounded hover:bg-gray-100" active-class="bg-gray-100 font-semibold">Users</router-link>
      </nav>
      
    </aside>

    <!-- Main -->
    <div class="flex-1 p-6">
      <header class="flex items-center justify-between mb-6">
        <div class="text-xl font-semibold">User Registration</div>
        <div class="hidden sm:block text-sm text-gray-500">Manage users and registrations</div>
      </header>

      <main>
        <router-view />
      </main>
    </div>
  </div>
</template>