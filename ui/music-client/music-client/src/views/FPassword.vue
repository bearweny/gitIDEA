<template>
  <el-container id="appSend">
    <el-header>
      <h1>修改密码</h1>
    </el-header>
    <el-main>
      <el-form @submit.prevent="handleSubmit">
        <el-form-item label="邮箱：" prop="email">
          <el-input id="email" v-model="email" type="email" required />
          <el-button @click="sendVerificationCode">发送验证码</el-button>
        </el-form-item>
        <el-form-item label="验证码：" prop="code">
          <el-input id="code" v-model="code" type="text" required />
        </el-form-item>
        <el-form-item label="新密码：" prop="password">
          <el-input id="password" v-model="password" type="password" required />
        </el-form-item>
        <el-form-item label="确认密码：" prop="confirmPassword">
          <el-input id="confirmPassword" v-model="confirmPassword" type="password" required />
        </el-form-item>
        <el-form-item>
          
          <el-button @click="handleSubmit" type="submit">提交</el-button>
          
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>


<style>
#appSend {
  max-width: 400px;
  margin: 0 auto;
}
</style>

<script>
import axios from 'axios';
export default {
  

  data() {
  
    return {
      email: "",
      code: "",
      password: "",
      confirmPassword: ""
    };
  },
  methods: {
    async sendVerificationCode() {
      if(email==null || email==""||email===""){
        this.$message({
          message:"邮箱为空",
          type: 'error'
        });
        return
      }
      try {
        const email = document.getElementById('email').value;
        const result = await axios.get(`http://localhost:8888/consumer/sendVerificationCode/${email}`);

        this.$message({
          message: result.data.message,
          type: result.data.success ? 'success' : 'error'
        });
      } catch (error) {
        console.error('Error submitting email:');
        this.$message({
          message: 'response.data',
          type: 'error'
        });
      }
    },


    async handleSubmit() {
  try {
    const email =document.getElementById('email').value;
    const code=document.getElementById('code').value
    const password=document.getElementById('password').value
    const confirmPassword=document.getElementById('confirmPassword').value
    const data = {
      email: email,
      code: code,
      password: password,
      confirmPassword: confirmPassword
    };
    // 发请求
    const result = await axios.post('http://localhost:8888/consumer/resetPassword', data);

    //提示
    this.$message({
      message: result.data.message,
      type: result.data.success ? 'success' : 'error'
    });

  //   跳转到路由
    if(result.data.success){
      // 存储用户名
      location.href="http://localhost:8081/sign-in"
    }

  } catch (error) {
    this.$message({
      message: result.data.message,
      type:'error'
    });
  }
}

},
};
</script>
