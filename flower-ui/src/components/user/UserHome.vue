<template>
  <div class="whole">
    <el-container>
      <el-header>
        <div><b style="float: left;margin-top: -20px"><h2>心语花艺销售平台</h2></b>
<!--          <b>用户操作台</b>-->
          <el-button class="logout" type="warning" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <!--    #575757   -->
          <el-menu
            router
            :default-active="defaultUrl"
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
          >


            <el-menu-item index="goods">
              <i class="el-icon-menu"></i>
              商品列表
            </el-menu-item>

            <el-menu-item index="cart">
              <i class="el-icon-document"></i>&nbsp;&nbsp;
              <span>购物车</span>&nbsp;&nbsp;
            </el-menu-item>

            <el-menu-item index="order">
              <i class="el-icon-document"></i>
              <span>我的订单</span>
            </el-menu-item>

            <el-menu-item index="updateInfo">
              <i class="el-icon-document"></i>
              <span>个人信息</span>
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
    data() {
      return {
        activeIndex: 1,
        defaultUrl: 'goods',
      }
    },
    // 监听路由变化
    watch: {
      '$route': 'getPath'
    },
    methods: {
      getPath() {
        this.defaultUrl = this.$route.path.replace('/', '');
        console.log(this.defaultUrl)
      },
      changePage(path) {
        this.$router.push(path)
      },
      logout() {
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
