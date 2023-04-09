<template>
  <div class="whole">
    <div class="login">
      <h3 class="title">心语花艺商城</h3>
      <div class="my-form">
        <el-form :model="loginForm" status-icon size="small">
          <el-form-item prop="account">
            <el-input type="text" prefix-icon="el-icon-user-solid" v-model="loginForm.account"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" prefix-icon="el-icon-s-cooperation" v-model="loginForm.password"
                      autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item class="btns">
            <el-radio v-model="loginForm.role" label="user">用户</el-radio>
            <el-radio v-model="loginForm.role" label="admin">管理员</el-radio>
          </el-form-item>
          <el-form-item class="btns">
            <el-button type="primary" @click="login()">登录</el-button>
            <el-button type="warning" @click="reset()">重置</el-button>
            <el-button type="success" @click="create">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--   注册   -->
    <div >
      <el-dialog
        title="注册用户"
        :visible.sync="createDialogVisible"
        width="30%"
        center
      >
        <div >
          <el-form :model="addUser" label-width="80px" size="small">

            <el-form-item label="账号">
              <el-input class="mid-input" v-model="addUser.account"></el-input>
            </el-form-item>

            <el-form-item label="姓名">
              <el-input class="mid-input" v-model="addUser.name"></el-input>
            </el-form-item>

            <el-form-item label="密码">
              <el-input class="mid-input" v-model="addUser.password"></el-input>
            </el-form-item>

            <el-form-item label="手机号">
              <el-input class="mid-input" type="number" v-model="addUser.phone"></el-input>
            </el-form-item>

            <el-form-item label="地址">
              <el-input class="mid-input" v-model="addUser.address"></el-input>
            </el-form-item>

            <div class="create-dialog-btn">
              <el-button type="primary" @click="doCreate">注册</el-button>
              <el-button type="warning" @click="cancel">取消</el-button>
            </div>

          </el-form>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Login',
    data() {
      return {
        createDialogVisible: false,
        addUser: {
          account: '',
          name: '',
          password: '',
          phone: '',
          address: ''
        },
        loginForm: {
          account: '',
          password: '',
          role: 'user',
        },
      }
    },
    methods: {
      changePage(path) {
        this.$router.push(path)
      },
      create() {
        this.reset();
        this.createDialogVisible = true;
      },
      verify(user) {
        if (user.account.length < 4 || user.password.length < 4 || user.name.length < 2 || user.phone.length !== 11 || user.address.length < 3) {
          this.$message.warning('表单格式不正确，请修改');
          return false;
        }
        return true;
      },
      doCreate() {
        let flag = this.verify(this.addUser);
        if (flag) {
          this.$http.post("/user/create", this.addUser).then(result => {
            let R = result.data; // R
            if (R.code === 2000) {
              this.$message.success(R.msg);
              this.refresh(1);
            } else {
              this.$message.error(R.msg);
            }
            this.createDialogVisible = false;
          });
        }
      },
      cancel() {
        this.createDialogVisible = false;
      },
      login() {
        this.$http.post("/login/doLogin", this.loginForm).then(result => {
          // 返回的 result.data = 后端的 R
          let R = result.data;
          let user = R.data;
          if (R.code === 2000) {
            window.sessionStorage.setItem('token', user.account);// 存了 account
            window.sessionStorage.setItem('role', user.role);// 存了 account
            this.$message.success(R.msg);
            if (user.role === 'admin') {
              this.$router.push('/home');
            } else if (user.role === 'user') {
              this.$router.push('/user');
            } else {
              this.$router.push('/404');
            }
          } else {
            this.$message.error(R.msg);
          }
        })
      },
      reset() {
        this.loginForm = {
          account: '',
          password: '',
          role: 'user',
        };
      }
    }
  }
</script>

<style scoped>
  body{

  }
  .whole {
    background-image: url("~@/assets/imgs/bghalf-2.jpg") ;
    background-size:100%;
    height: 100%;
  }

  .login {
    width: 450px;
    height: 300px;
    background-color: #fff;
    border-radius: 30px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .title {
    margin-top: 30px;
    text-align: center;
    color: dodgerblue;
  }

  .my-form {
    margin-top: 75px;
    width: 300px;
    position: absolute;
    left: 50%;
    top: 36%;
    transform: translate(-50%, -50%);
  }

  .btns {
    text-align: center;
  }

  .el-form-item {
    margin-left:2%;
  }
</style>
