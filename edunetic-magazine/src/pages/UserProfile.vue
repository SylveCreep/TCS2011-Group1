<template>
    <user-update v-bind:loggedUser="currentUser"></user-update>
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
      currentUser: this.$cookies.get("id")
    };
  },
};
</script>

<style scoped>
</style>
