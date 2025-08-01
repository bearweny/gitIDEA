<template>
  <el-form ref="updateForm" label-width="70px" :model="registerForm" :rules="SignUpRules">
    <el-form-item prop="username" label="用户名">
      <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="registerForm.sex">
        <el-radio :label="0">女</el-radio>
        <el-radio :label="1">男</el-radio>
        <el-radio :label="2">保密</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item prop="birth" label="生日">
      <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width: 100%"></el-date-picker>
    </el-form-item>
    <el-form-item prop="introduction" label="签名">
      <el-input type="textarea" placeholder="签名" v-model="registerForm.introduction"></el-input>
    </el-form-item>
    <el-form-item prop="location" label="地区">
      <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%">
        <el-option v-for="item in AREA" :key="item.value" :label="item.label" :value="item.value"> </el-option>
      </el-select>
    </el-form-item>
    <el-form-item prop="phoneNum" label="手机">
      <el-input placeholder="手机" v-model="registerForm.phoneNum"></el-input>
    </el-form-item>
    <el-form-item prop="email" label="邮箱">
      <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="goBack(-1)">取消</el-button>
      <el-button type="primary" @click="saveMsg()">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts">
import { defineComponent, computed, onMounted, getCurrentInstance, reactive } from "vue";
import { useStore } from "vuex";
import mixin from "@/mixins/mixin";
import { AREA, SignUpRules } from "@/enums";
import { HttpManager } from "@/api";
import { getBirth } from "@/utils";

export default defineComponent({
  setup() {
    const { proxy } = getCurrentInstance();
    const store = useStore();
    const { goBack } = mixin();

    // 注册
    const registerForm = reactive({
      username: "",
      sex: "",
      phoneNum: "",
      email: "",
      birth: new Date(),
      introduction: "",
      location: "",
      userPic: "",
    });
    const userId = computed(() => store.getters.userId);

    async function getUserInfo(id) {
      const result = (await HttpManager.getUserOfId(id)) as ResponseBody;
      registerForm.username = result.data[0].username;
      registerForm.sex = result.data[0].sex;
      registerForm.phoneNum = result.data[0].phoneNum;
      registerForm.email = result.data[0].email;
      registerForm.birth = result.data[0].birth;
      registerForm.introduction = result.data[0].introduction;
      registerForm.location = result.data[0].location;
      registerForm.userPic = result.data[0].avator;
    }

    async function saveMsg() {
      let canRun = true;
      (proxy.$refs["updateForm"] as any).validate((valid) => {
        if (!valid) return (canRun = false);
      });
      if (!canRun) return;


      const id = userId.value;
      const username = registerForm.username;
      const sex = registerForm.sex;
      const phoneNum = registerForm.phoneNum;
      const email = registerForm.email;
      const birth = registerForm.birth;
      const introduction = registerForm.introduction;
      const location = registerForm.location;
      //发送请求
      //提示信息
      //跳转
    }

    onMounted(() => {
      getUserInfo(userId.value);
    });

    return {
      AREA,
      registerForm,
      SignUpRules,
      saveMsg,
      goBack,
    };
  },
});
</script>

<style lang="scss" scoped>
.btn ::v-deep .el-form-item__content {
  display: flex;
  justify-content: center;
}
</style>
