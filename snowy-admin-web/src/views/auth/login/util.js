import loginApi from '@/api/auth/loginApi'
import userCenterApi from '@/api/sys/userCenterApi'
import dictApi from '@/api/dev/dictApi'
import router from '@/router'
import tool from '@/utils/tool'
import { message } from 'ant-design-vue'
import { useGlobalStore } from '@/store'
import routerUtil from '@/utils/routerUtil'

export const afterLogin = async (loginToken) => {
	tool.data.set('TOKEN', loginToken)
	// 获取登录的用户信息
	const loginUser = await loginApi.getLoginUser()
	const globalStore = useGlobalStore()
	globalStore.setUserInfo(loginUser)
	tool.data.set('USER_INFO', loginUser)

	// 获取用户的菜单
	const menu = await userCenterApi.userLoginMenu()
	let indexMenu = routerUtil.getIndexMenu(menu).path
	tool.data.set('MENU', menu)
	// 重置系统默认应用
	tool.data.set('SNOWY_MENU_MODULE_ID', menu[0].id)
	message.success('登录成功')
	if (tool.data.get('LAST_VIEWS_PATH')) {
		// 如果有缓存，将其登录跳转到最后访问的路由
		indexMenu = tool.data.get('LAST_VIEWS_PATH')
	}
	// 如果存在退出后换新账号登录，进行重新匹配，匹配无果则默认首页
	if (menu) {
		let routerTag = 0
		menu.forEach((item) => {
			if (item.children) {
				if (JSON.stringify(item.children).indexOf(indexMenu) > -1) {
					routerTag++
				}
			}
		})
		if (routerTag === 0) {
			// 取首页
			indexMenu = routerUtil.getIndexMenu(menu).path
		}
	}
	dictApi.dictTree().then((data) => {
		// 设置字典到store中
		tool.data.set('DICT_TYPE_TREE_DATA', data)
	})
	await router.replace({
		path: indexMenu
	})
}
