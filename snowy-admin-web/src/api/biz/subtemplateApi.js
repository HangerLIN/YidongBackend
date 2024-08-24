import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/SubTemplate/` + url, ...arg)

/**
 * SubTemplateApi接口管理器
 *
 * @author gaobaoqu
 * @date  2024/08/24 09:29
 **/
export default {
	// 获取SubTemplate分页
	subtemplatePage(data) {
		return request('page', data, 'get')
	},
	// 提交SubTemplate表单 edit为true时为编辑，默认为新增
	subtemplateSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除SubTemplate
	subtemplateDelete(data) {
		return request('delete', data)
	},
	// 获取SubTemplate详情
	subtemplateDetail(data) {
		return request('detail', data, 'get')
	}
}
