import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/basicinfo/` + url, ...arg)

/**
 * 项目基础信息Api接口管理器
 *
 * @author lth
 * @date  2024/07/18 19:32
 **/
export default {
	// 获取项目基础信息分页
	projectBasicInfoPage(data) {
		return request('page', data, 'get')
	},
	// 提交项目基础信息表单 edit为true时为编辑，默认为新增
	projectBasicInfoSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除项目基础信息
	projectBasicInfoDelete(data) {
		return request('delete', data)
	},
	// 获取项目基础信息详情
	projectBasicInfoDetail(data) {
		return request('detail', data, 'get')
	}
}
