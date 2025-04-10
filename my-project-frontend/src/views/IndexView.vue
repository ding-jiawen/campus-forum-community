<script setup>
import { logout, get } from '@/net'
import router from "@/router";
import {useStore} from "@/store";
import {reactive, ref} from "vue";
import {
  Bell,
  Calendar, CaretRight,
  ChatDotSquare, Comment, DataLine,
  Document,
  Location, Lock,
  Medal, Monitor, Notebook, Operation,
  Position,
  School, Search,
  Ship, User, UserFilled
} from "@element-plus/icons-vue";

const store = useStore()
/**
 * 加载数据过程中整个页面都不显示，加载完毕后显示
 * @type {Ref<UnwrapRef<boolean>, UnwrapRef<boolean> | boolean>}
 */
const loading = ref(true)

get('/api/user/info', (data) => {
  store.user = data
  loading.value = false
})

const searchInput = reactive( {
  type: '1',
  text: ''
})


function userLogout() {
  logout(() => router.push("/"))
}
</script>

<template>
  <div class="main-content" v-loading="loading" element-loading-text="正在进入，请稍后...">
    <el-container style="height: 100%" v-if="!loading">
      <el-header class="main-content-header">
        <el-image class="logo" src="https://element-plus.org/images/element-plus-logo.svg"></el-image>
        <div style="flex: 1; padding: 0 20px; text-align: center">
          <el-input v-model="searchInput.text" style="width: 100%; max-width: 500px" placeholder="搜索你感兴趣的内容...">
            <template #prefix>
              <el-icon><Search/></el-icon>
            </template>
            <template #append>
              <el-select style="width: 100px" v-model="searchInput.type">
                <el-option value="1" label="帖子广场"/>
                <el-option value="2" label="校园活动"/>
                <el-option value="3" label="表白墙"/>
                <el-option value="4" label="教务通知"/>
              </el-select>
            </template>
          </el-input>
        </div>
        <div class="user-info">
          <div class="profile">
            <div>{{store.user.username}}</div>
            <div>{{ store.user.email }}</div>
          </div>
          <el-dropdown>
            <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
            <template #dropdown>
              <el-dropdown-item>
                <el-icon><operation/></el-icon>
                个人设置
              </el-dropdown-item>
              <el-dropdown-item>
                <el-icon><comment/></el-icon>
                消息列表
              </el-dropdown-item>
              <el-dropdown-item @click="userLogout" divided>
                <el-icon><CaretRight /></el-icon>
                退出登录
              </el-dropdown-item>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="230px">
          <el-scrollbar style="height: calc(100vh - 55px)">
            <el-menu style="min-height: calc(100vh - 55px)" router :default-active="$route.path" >
              <el-sub-menu index="1">
                <template #title>
                  <el-icon><location/></el-icon>
                  <span><b>校园论坛</b></span>
                </template>
                <el-menu-item index="1-1">
                  <template #title>
                    <el-icon><chat-dot-square/></el-icon>
                    帖子广场
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Bell /></el-icon>
                    失物招领
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><School /></el-icon>
                    校园活动
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Ship /></el-icon>
                    表白墙
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Medal /></el-icon>
                    海文考研
                    <el-tag style="margin-left: 10px" size="small">合作机构</el-tag>
                  </template>
                </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="2">
                <template #title>
                  <el-icon><Position/></el-icon>
                  <span><b>探索与发现</b></span>
                </template>
                <el-menu-item>
                  <template #title>
                    <el-icon><Document /></el-icon>
                    成绩查询
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Calendar /></el-icon>
                    班级课程表
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Monitor /></el-icon>
                    教务通知
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Notebook /></el-icon>
                    在线图书馆
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><DataLine /></el-icon>
                    预约教室
                  </template>
                </el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="3">
                <template #title>
                  <el-icon><Operation /></el-icon>
                  <span><b>个人设置</b></span>
                </template>
                <el-menu-item index="/index/user-setting">
                  <template #title>
                    <el-icon><User /></el-icon>
                    个人信息设置
                  </template>
                </el-menu-item>
                <el-menu-item>
                  <template #title>
                    <el-icon><Lock /></el-icon>
                    账号安全设置
                  </template>
                </el-menu-item>
              </el-sub-menu>
            </el-menu>
          </el-scrollbar>

        </el-aside>
        <el-main class="main-content-page">
          <el-scrollbar style="height: calc(100vh - 55px)">
            <router-view v-slot="{ Component }">
              <transition name="el-fade-in-linear" mode="out-in">
                <component :is="Component" style="height: 100%"/>
              </transition>
            </router-view>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style lang="less" scoped>
.main-content {
  height: 100vh;
  width: 100vw;
}

.main-content-page {
  padding: 0;
  background-color: #f7f8fa;
}

.dark .main-content-page {
  background-color: #212225;
}

.main-content-header {
  border-bottom: solid 1px var(--el-border-color);
  height: 55px;
  display: flex;
  align-items: center;
  box-sizing: border-box;

  .logo {
    height: 32px;
  }

  .user-info {
    display: flex;
    justify-content: flex-end;
    align-items: center;

    .el-avatar:hover {
      cursor: pointer;
    }
  }

  .profile {
    text-align: right;
    margin-right: 20px;

    :first-child {
      font-size: 18px;
      font-weight: bold;
      line-height: 20px;
    }

    :last-child {
      font-size: 10px;
      color: grey;
    }
  }
}
</style>
