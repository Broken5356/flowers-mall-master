<template>
  <div class="whole">
    <el-container>
      <el-header>
        <div>
          <b style="float: left;margin-top: -20px">
          <h2>心语花艺后台管理系统</h2></b>
          <b>管理操作台</b>
          <el-button class="logout" type="warning" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <!--    #575757   -->
          <el-menu
            router
            default-active="index"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">

            <el-menu-item index="index">
              <i class="el-icon-menu"></i>
              网站首页
            </el-menu-item>

            <el-menu-item index="users">
              <i class="el-icon-document"></i>
              <span>用户管理</span>
            </el-menu-item>

            <el-menu-item index="flowers">
              <i class="el-icon-document"></i>
              <span>商品管理</span>
            </el-menu-item>

            <el-menu-item index="flower_type">
              <i class="el-icon-document"></i>
              <span>种类管理</span>
            </el-menu-item>

            <el-menu-item index="orders">
              <i class="el-icon-document"></i>
              <span>订单管理</span>
            </el-menu-item>

          </el-menu>
        </el-aside>
        <el-container>
          <!--     主体区域     -->
          <el-main>
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
      <el-footer>CopyRight © 2022 Broken奇思妙语</el-footer>
    </el-container>

  </div>
</template>

<script>
  export default {
    name: 'App',
    data() {
      return {
        activeIndex: 1,
      }
    },
    methods: {
      changePage(path) {
        this.$router.push(path)
      },
      logout(){
        this.$router.push('/login');
      },
    },
  }
</script>

<style scoped>
  .whole {
    height: 100%;
  }

  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-menu {
    border-right: none;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 20px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container .el-aside {
    line-height: 320px;
  }

  .my-menu {
    position: absolute;
    margin-top: 10px;
    margin-left: 10px;
  }

  .main {
    height: 80%;
  }

  .el-container {
    height: 100%;
  }

  .el-header {
    background-color: skyblue;
  }

  .el-aside {
    height: 100%;
    background-color: #545c64;
  }

  .el-main {
    background-color: white;
  }

  .menu-child-item {
    margin-left: 20px;
  }

  .el-footer {
    background-color: cornflowerblue;
  }

  .welcome {
    float: left;
    color: cornflowerblue;
  }
</style>
