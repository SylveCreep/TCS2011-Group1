<template>
    <user-update v-bind:loginUserId="currentUserId"></user-update>
</template>

<script>
import UserUpdate from "./user/UserUpdate"; 

export default {
  name: "UserProfile",
  components: {
    UserUpdate
  },
  data() {
    return {
      currentUserId: this.$cookies.get("id")
    };
  },
};
</script>

<style scoped>
</style>
