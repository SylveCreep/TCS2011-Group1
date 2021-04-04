<template>
  <div>
    <label for="exampleFile" class="col-sm-4 control-label"
      ><b>Terms and Conditions:</b>
    </label>
    <div class="row">
      <textarea class="input-file" cols="100" rows="6">
            We will not be liable or responsible for any failure to perform, 
            or delay in performance of, the Services, Surveys or any of our obligations 
            under these Terms, nor for any defect or damage to Products, 
            or for any failure or delay in supply or delivery of Products, 
            in each case that is caused by events outside our reasonable control 
            (including, without limitation, the following to the extent these are outside our reasonable control: accidents, 
            extreme weather conditions, fire, explosion, flood, storm, earthquake, natural disaster,
            failure of telecommunications networks, inability to use transport networks, acts of God, 
            terrorist attack, war, civil commotion, riots, strikes, lockouts and other industrial disputes, 
            acts or restraints of Government, and imposition of restrictions on imports or exports).
        </textarea
      >
    </div>
  </div>
</template>
<script>
export default {
  name: "TermCondition",
};
</script>

<style scoped>
.input-file {
    margin-left: 30px;
    padding: 10px;
}
</style>