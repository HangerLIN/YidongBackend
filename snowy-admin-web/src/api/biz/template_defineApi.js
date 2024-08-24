import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/template_define/` + url, ...arg)

/**
 * template_defineApi接口管理器
 *
 * @author gaobaoqu
 * @date  2024/08/23 23:09
 **/
export default {
	// 获取template_define分页
	template_definePage(data) {
		return request('page', data, 'get')
	},
	// 提交template_define表单 edit为true时为编辑，默认为新增
	template_defineSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除template_define
	template_defineDelete(data) {
		return request('delete', data)
	},
	// 获取template_define详情
	template_defineDetail(data) {
		return request('detail', data, 'get')
	}
}
