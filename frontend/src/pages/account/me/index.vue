<script setup lang="ts">


</script>

<template>
  <div>
    <h1>ACCOUNT</h1>

    <div>
      <h2>Names</h2>
      <div>
        <form action="">
          <div>
            <label for="firstName">first name <span>(optional)</span></label>
            <input type="text" name="firstName" id="firstName" />
          </div>
          <div>
            <label for="lastName">last name <span>(optional)</span></label>
            <input type="text" name="lastName" id="lastName" />
          </div>
          <div>
            <label for="displayName">display name <span>(optional)</span></label>
            <input type="text" name="displayName" id="displayName" />
          </div>
        </form>
      </div>
    </div>


  </div>
</template>

<style scoped lang="scss">

form {
  div {
    display: flex;
    flex-direction: column;
    width: 16rem;
    margin-bottom: 1rem;

    span {
      font-size: 0.8rem;
      font-style: italic;
    }
  }

}
</style>
