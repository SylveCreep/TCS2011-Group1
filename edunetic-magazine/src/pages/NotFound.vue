<template>
    <div class="not-found">
        <h1><i class="fas fa-exclamation-triangle"></i></h1>
        <h3>Oops! This Page Could Not Be Found</h3>
        <p>SORRY BUT THE PAGE YOU ARE LOOKING FOR DOES NOT EXIST OR YOU DON'T HAVE PERMISSION TO ACCESS</p>
        <router-link to="/" class="btn btn-primary">
            Back to homepage
        </router-link>
    </div>
</template>
<script>
export default {
    name: "NotFound"
}
</script>
<style scoped>
    .not-found{
        text-align: center;
    }
    h1 {
        color:red;
        font-size: 200px;
    }
    h3 {
        color: red;
        font-weight: bolder;
    }
</style>