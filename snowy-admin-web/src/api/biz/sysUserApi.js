import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/user_test/` + url, ...arg)

/**
 * 用户Api接口管理器
 *
 * @author Hanger
 * @date  2024/07/14 23:38
 **/
export default {
	// 获取用户分页
	sysUserPage(data) {
		return request('page', data, 'get')
	},
	// 提交用户表单 edit为true时为编辑，默认为新增
	sysUserSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除用户
	sysUserDelete(data) {
		return request('delete', data)
	},
	// 获取用户详情
	sysUserDetail(data) {
		return request('detail', data, 'get')
	}
}
