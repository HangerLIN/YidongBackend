import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/template1/` + url, ...arg)

/**
 * template1Api接口管理器
 *
 * @author gaobaoqu
 * @date  2024/08/24 08:36
 **/
export default {
	// 获取template1分页
	template1Page(data) {
		return request('page', data, 'get')
	},
	// 提交template1表单 edit为true时为编辑，默认为新增
	template1SubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除template1
	template1Delete(data) {
		return request('delete', data)
	},
	// 获取template1详情
	template1Detail(data) {
		return request('detail', data, 'get')
	}
}
