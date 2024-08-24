import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/Template2/` + url, ...arg)

/**
 * Template2Api接口管理器
 *
 * @author gaobaoqu
 * @date  2024/08/24 09:07
 **/
export default {
	// 获取Template2分页
	template2Page(data) {
		return request('page', data, 'get')
	},
	// 提交Template2表单 edit为true时为编辑，默认为新增
	template2SubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除Template2
	template2Delete(data) {
		return request('delete', data)
	},
	// 获取Template2详情
	template2Detail(data) {
		return request('detail', data, 'get')
	}
}
