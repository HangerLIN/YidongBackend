import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/SubTemplate01/` + url, ...arg)

/**
 * SubTemplate01Api接口管理器
 *
 * @author gaobaoqu
 * @date  2024/08/24 09:34
 **/
export default {
	// 获取SubTemplate01分页
	subtemplate01Page(data) {
		return request('page', data, 'get')
	},
	// 提交SubTemplate01表单 edit为true时为编辑，默认为新增
	subtemplate01SubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除SubTemplate01
	subtemplate01Delete(data) {
		return request('delete', data)
	},
	// 获取SubTemplate01详情
	subtemplate01Detail(data) {
		return request('detail', data, 'get')
	}
}
