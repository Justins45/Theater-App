<script setup lang="ts">
import { ref, reactive } from 'vue'
import apiClient from '@/api/axios'

const formInfo = reactive({
  firstName: null,
  lastName: null,
  displayName: null
})

const removeName = reactive({
  firstName: false,
  lastName: false,
  displayName: false
})

const responseMessage = ref()

async function getInformation() {
  const res = await apiClient.get("/patron")
  formInfo.firstName = res.data.firstName
  formInfo.lastName = res.data.lastName
  formInfo.displayName = res.data.displayName

}

getInformation()

async function clearNames() {
  try {
    const res = await apiClient.patch("/patron/remove-naming", {
      'firstName': removeName.firstName,
      'lastName': removeName.lastName,
      'displayName': removeName.displayName
    })
    responseMessage.value = `Successfully updated ${res.data.message}`
    await getInformation()
  } catch (e) {
    console.error(e)
  }
}


async function handleNameChange() {

  try {
    const res = await apiClient.patch("/patron/update-naming", {
        'firstName': formInfo.firstName,
        'lastName': formInfo.lastName,
        'displayName': formInfo.displayName
    })
    responseMessage.value = `Successfully updated ${res.data.message}`
    await getInformation()
  } catch (e) {
    console.error(e)
  }

}

</script>

<template>
  <div>
    <h1>ACCOUNT</h1>

    <div>
      <h2>Names</h2>
      <div>
        <form id="nameChangeForm" @submit.prevent="handleNameChange">
        </form>
        <form id="nameRemovalForm" @submit.prevent="clearNames">
          <div>
            <label for="firstName">first name <span>(optional)</span></label>
            <input
              id="firstName"
              type="text"
              name="firstName"
              placeholder="e.g., John"
              v-model="formInfo.firstName"
              form="nameChangeForm"
            />

            <label for="removeFistName">remove first name</label>
            <input
              id="removeFistName"
              type="checkbox"
              name="removeFistName"
              v-model="removeName.firstName"
              form="clearNames"
            />
          </div>
          <div>
            <label for="lastName">last name <span>(optional)</span></label>
            <input
              id="lastName"
              type="text"
              name="lastName"
              placeholder="e.g., Doe"
              v-model="formInfo.lastName"
              form="nameChangeForm"
            />

            <label for="removeLastName">remove last name</label>
            <input
              id="removeLastName"
              type="checkbox"
              name="removeLastName"
              v-model="removeName.lastName"
              form="clearNames"
            />
          </div>
          <div>
            <label for="displayName">display name <span>(optional)</span></label>
            <input
              id="displayName"
              type="text"
              name="displayName"
              placeholder="e.g., JD"
              v-model="formInfo.displayName"
              form="nameChangeForm"
            />

            <label for="removeDisplayName">remove display name</label>
            <input
              id="removeDisplayName"
              type="checkbox"
              name="removeDisplayName"
              v-model="removeName.displayName"
              form="clearNames"
            />

          </div>
          <button type="submit" form="nameChangeForm">Update Information</button>
          <button type="submit" form="nameRemovalForm">Clear selected names</button>
        </form>
        <p v-if="responseMessage">{{ responseMessage }}</p>
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
