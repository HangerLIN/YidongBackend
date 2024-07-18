<template>
	<a-card :bordered="false">
		<s-table
			ref="tableRef"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.id"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('sysUserAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('sysUserBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchSysUser"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('sysUserEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['sysUserEdit', 'sysUserDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteSysUser(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('sysUserDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="tableRef.refresh()" />
</template>

<script setup name="user_test">
	import { cloneDeep } from 'lodash-es'
	import Form from './form.vue'
	import sysUserApi from '@/api/biz/sysUserApi'
	const tableRef = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '头像',
			dataIndex: 'avatar'
		},
		{
			title: '签名',
			dataIndex: 'signature'
		},
		{
			title: '账号',
			dataIndex: 'account'
		},
		{
			title: '密码',
			dataIndex: 'password'
		},
		{
			title: '姓名',
			dataIndex: 'name'
		},
		{
			title: '昵称',
			dataIndex: 'nickname'
		},
		{
			title: '性别',
			dataIndex: 'gender'
		},
		{
			title: '年龄',
			dataIndex: 'age'
		},
		{
			title: '出生日期',
			dataIndex: 'birthday'
		},
		{
			title: '民族',
			dataIndex: 'nation'
		},
		{
			title: '籍贯',
			dataIndex: 'nativePlace'
		},
		{
			title: '家庭住址',
			dataIndex: 'homeAddress'
		},
		{
			title: '通信地址',
			dataIndex: 'mailingAddress'
		},
		{
			title: '证件类型',
			dataIndex: 'idCardType'
		},
		{
			title: '证件号码',
			dataIndex: 'idCardNumber'
		},
		{
			title: '文化程度',
			dataIndex: 'cultureLevel'
		},
		{
			title: '政治面貌',
			dataIndex: 'politicalOutlook'
		},
		{
			title: '毕业院校',
			dataIndex: 'college'
		},
		{
			title: '学历',
			dataIndex: 'education'
		},
		{
			title: '学制',
			dataIndex: 'eduLength'
		},
		{
			title: '学位',
			dataIndex: 'degree'
		},
		{
			title: '手机',
			dataIndex: 'phone'
		},
		{
			title: '邮箱',
			dataIndex: 'email'
		},
		{
			title: '家庭电话',
			dataIndex: 'homeTel'
		},
		{
			title: '办公电话',
			dataIndex: 'officeTel'
		},
		{
			title: '紧急联系人',
			dataIndex: 'emergencyContact'
		},
		{
			title: '紧急联系人电话',
			dataIndex: 'emergencyPhone'
		},
		{
			title: '紧急联系人地址',
			dataIndex: 'emergencyAddress'
		},
		{
			title: '员工编号',
			dataIndex: 'empNo'
		},
		{
			title: '入职日期',
			dataIndex: 'entryDate'
		},
		{
			title: '机构id',
			dataIndex: 'orgId'
		},
		{
			title: '职位id',
			dataIndex: 'positionId'
		},
		{
			title: '职级',
			dataIndex: 'positionLevel'
		},
		{
			title: '主管id',
			dataIndex: 'directorId'
		},
		{
			title: '兼任信息',
			dataIndex: 'positionJson'
		},
		{
			title: '上次登录ip',
			dataIndex: 'lastLoginIp'
		},
		{
			title: '上次登录地点',
			dataIndex: 'lastLoginAddress'
		},
		{
			title: '上次登录时间',
			dataIndex: 'lastLoginTime'
		},
		{
			title: '上次登录设备',
			dataIndex: 'lastLoginDevice'
		},
		{
			title: '最新登录ip',
			dataIndex: 'latestLoginIp'
		},
		{
			title: '最新登录地点',
			dataIndex: 'latestLoginAddress'
		},
		{
			title: '最新登录时间',
			dataIndex: 'latestLoginTime'
		},
		{
			title: '最新登录设备',
			dataIndex: 'latestLoginDevice'
		},
		{
			title: '用户状态',
			dataIndex: 'userStatus'
		},
		{
			title: '排序码',
			dataIndex: 'sortCode'
		},
		{
			title: '扩展信息',
			dataIndex: 'extJson'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['sysUserEdit', 'sysUserDelete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: 150
		})
	}
	const selectedRowKeys = ref([])
	// 列表选择配置
	const options = {
		// columns数字类型字段加入 needTotal: true 可以勾选自动算账
		alert: {
			show: true,
			clear: () => {
				selectedRowKeys.value = ref([])
			}
		},
		rowSelection: {
			onChange: (selectedRowKey, selectedRows) => {
				selectedRowKeys.value = selectedRowKey
			}
		}
	}
	const loadData = (parameter) => {
		return sysUserApi.sysUserPage(parameter).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		tableRef.value.refresh(true)
	}
	// 删除
	const deleteSysUser = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		sysUserApi.sysUserDelete(params).then(() => {
			tableRef.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchSysUser = (params) => {
		sysUserApi.sysUserDelete(params).then(() => {
			tableRef.value.clearRefreshSelected()
		})
	}
</script>
