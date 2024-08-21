import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/basicinfo/` + url, ...arg)
const request1 = (url, ...arg) => baseRequest(`/biz/subjectinfoinfo/` + url, ...arg)
const saveAndReturn = (url, ...arg) => baseRequest(`/biz/income/` + url,  ...arg)
const request2 = (url, ...arg) => baseRequest(`/biz/subjectinfoinfo/` + url, ...arg,"get")

const subprojectSpend = (url, ...arg) => baseRequest(`/biz/spend/` + url,  ...arg)
/**
 * 项目基础信息Api接口管理器
 *
 * @author lth
 * @date  2024/07/19 21:16
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
	},

	// 提交子项目信息
	addSubProject(data, edit = false) {
		return request1(edit ? 'edit' : 'add', data)
	},

	//子项目计算（不含）saveAndReturn
	investAmountSubject(data) {
		return request1('investAmount',data)
	},
	saveAndReturn(data) {
		return saveAndReturn('saveAndReturn',data)
	},
	searchSubProject(data, edit = false) {
		return request2('select',data)
	},
	subprojectSpend(data){
		return subprojectSpend('save',data)
	}
}

