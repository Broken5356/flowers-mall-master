<template>
  <div>
    <el-card>
      <div class="info-first-card">
        <h2 class="user-info-title">我的个人信息</h2>
        <el-button class="save" @click="update" type="primary" style="margin-bottom: 20px">修改</el-button>
      </div>
    </el-card>
    <el-card class="box-card user-info-card">
      <div>
        <div class="user-info-data">
          <el-form :model="user" label-width="120px">
            <el-form-item label="姓名：">
              {{user.name}}
            </el-form-item>
            <el-form-item label="账号：">
              {{user.account}}
            </el-form-item>
            <el-form-item label="密码：">
              {{user.password}}
            </el-form-item>
            <el-form-item label="手机：">
              {{user.phone}}
            </el-form-item>
            <el-form-item label="地址：">
              {{user.address}}
            </el-form-item>
          </el-form>

        </div>
      </div>
      <div>
        <el-dialog
          align="center"
          title="修改用户信息"
          :visible.sync="updateDialogVisible"
          width="50%"
        >
          <div>
            <el-form :model="updateUser" label-width="80px" size="small">

              <el-form-item  label="">
                <el-input class="small-input" type="hidden" v-model="updateUser.id"></el-input>
              </el-form-item>

              <el-form-item class="el-form-item-i" label="账号">
                <el-input class="small-input" readonly="readonly" v-model="updateUser.account"></el-input>
              </el-form-item>

              <el-form-item class="el-form-item-i" label="姓名">
                <el-input class="small-input" v-model="updateUser.name"></el-input>
              </el-form-item>

              <el-form-item class="el-form-item-i" label="密码">
                <el-input class="small-input" v-model="updateUser.password"></el-input>
              </el-form-item>

              <el-form-item class="el-form-item-i" label="手机号">
                <el-input class="small-input" type="number" v-model="updateUser.phone"></el-input>
              </el-form-item>

              <el-form-item class="el-form-item-i" label="地址">
                <el-input class="small-input" v-model="updateUser.address"></el-input>
              </el-form-item>

              <div class="create-dialog-btn">
                <el-button type="primary" @click="doUpdate">保存</el-button>
                <el-button type="warning" @click="cancel">取消</el-button>
              </div>

            </el-form>
          </div>
        </el-dialog>
      </div>
    </el-card>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        user: {
          id: "",
          account: window.sessionStorage.getItem('token'),
          name: '',
          password: '',
          phone: '',
          address: '',
        },
        updateDialogVisible: false,
        updateUser: {
          id: '',
          account: '',
          name: '',
          password: '',
          phone: '',
          address: ''
        },
      }
    },
    methods: {
      cancel() {
        this.updateDialogVisible = false;
        this.createDialogVisible = false;
      },
      update() {
        this.updateDialogVisible = true;
        this.updateUser.id = this.user.id;
        this.updateUser.name = this.user.name;
        this.updateUser.account = this.user.account;
        this.updateUser.password = this.user.password;
        this.updateUser.phone = this.user.phone;
        this.updateUser.address = this.user.address;
      },
      doUpdate() {
        this.$http.put("/user/update", this.updateUser).then(res => {
          let ans = res.data;
          if (ans.code === 2000) {
            this.freshPage();
            this.updateDialogVisible = false;
            this.$message.success(ans.msg);
          } else {
            this.$message.error(ans.msg);
          }
        })
      },
      freshPage() {
        this.$http.get("/user/queryInfoByAccount?account=" + window.sessionStorage.getItem('token')).then(res => {
          let R = res.data; // R
          this.user = R.data;
        })
      },
    },
    created() {
      this.freshPage();
    }
  }
</script>

<style scoped>
  .user-info-card {
    margin-top: 60px;
  }

  .user-info-title {
    color: cornflowerblue;
    float: left;
    margin-top: -5px;
  }

  .user-info-data {
    margin-top: 15px;
    margin-left: 45%;
  }

  br {
    margin-top: 10px;
  }

  b {
    margin-left: 100px;
  }

  .el-form-item {
    width: 300px;
  }

  .save {
    float: right;
    margin-right: 10px;
    margin-top: -10px;
  }
  .el-form-item{
    margin-left: -15%;
  }

  .el-form-item-i{
    margin-left: 3%;
  }
</style>
