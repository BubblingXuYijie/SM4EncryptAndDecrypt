<template>
  <div>
    <span>未加密的密码：</span><input type="text" v-model="this.password">
    <button @click="encrypt">加密</button>
    <br>
    <span>加密后的密码：{{encryptedPassword}}</span>
    <br>
    <span>解密后的密码：{{decryptedPassword}}</span>
  </div>
</template>

<script>
import {SM4Util} from "../utils/sm4";
import {ref} from "vue";
export default {
  name: "HelloWorld",
  setup(){
    const password = ref('');
    const encryptedPassword = ref('');
    const decryptedPassword = ref('');

    return{
      password,
      encryptedPassword,
      decryptedPassword,
    }
  },
  methods:{
    encrypt(){
      const sm4 = new SM4Util();
      this.encryptedPassword = sm4.encryptData_CBC(this.password)
      this.decryptedPassword = sm4.decryptData_CBC(this.encryptedPassword)
    }
  }
}
</script>

<style scoped>
a {
  color: #42b983;
}
</style>
