import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/SubTemplate001/` + url, ...arg)

/**
 * SubTemplate001Api接口管理器
 *
 * @author gaobaoqu
 * @date  2024/08/24 10:27
 **/
export default {
	// 获取SubTemplate001分页
	subtemplate001Page(data) {
		return request('page', data, 'get')
	},
	// 提交SubTemplate001表单 edit为true时为编辑，默认为新增
	subtemplate001SubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除SubTemplate001
	subtemplate001Delete(data) {
		return request('delete', data)
	},
	// 获取SubTemplate001详情
	subtemplate001Detail(data) {
		return request('detail', data, 'get')
	}
}
