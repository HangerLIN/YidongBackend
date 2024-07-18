<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="项目经理" name="projectManager">
						<a-input v-model:value="searchFormState.projectManager" placeholder="请输入项目经理" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="项目编码" name="projectCode">
						<a-input v-model:value="searchFormState.projectCode" placeholder="请输入项目编码" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="tableRef.refresh()">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('projectBasicInfoAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('projectBasicInfoBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchProjectBasicInfo"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('projectBasicInfoEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['projectBasicInfoEdit', 'projectBasicInfoDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteProjectBasicInfo(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('projectBasicInfoDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="tableRef.refresh()" />
</template>

<script setup name="project_basic">
	import { cloneDeep } from 'lodash-es'
	import Form from './form.vue'
	import projectBasicInfoApi from '@/api/biz/projectBasicInfoApi'
	const searchFormState = ref({})
	const searchFormRef = ref()
	const tableRef = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '名称',
			dataIndex: 'name'
		},
		{
			title: '公司名称',
			dataIndex: 'companyName'
		},
		{
			title: '下属',
			dataIndex: 'subordinate'
		},
		{
			title: '电话',
			dataIndex: 'phone'
		},
		{
			title: '项目经理',
			dataIndex: 'projectManager'
		},
		{
			title: '项目经理电话',
			dataIndex: 'projectManagerPhone'
		},
		{
			title: '项目经理单位',
			dataIndex: 'projectManagerUnit'
		},
		{
			title: '项目类型1',
			dataIndex: 'projectType1'
		},
		{
			title: '项目类型2',
			dataIndex: 'projectType2'
		},
		{
			title: '项目名称',
			dataIndex: 'projectName'
		},
		{
			title: '项目编码',
			dataIndex: 'projectCode'
		},
		{
			title: '评估周期',
			dataIndex: 'evaluationPeriod'
		},
		{
			title: '资源周期',
			dataIndex: 'resourcePeriod'
		},
		{
			title: '建设周期',
			dataIndex: 'buildPeriod'
		},
		{
			title: '评估时间',
			dataIndex: 'evaluationTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['projectBasicInfoEdit', 'projectBasicInfoDelete'])) {
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
		const searchFormParam = cloneDeep(searchFormState.value)
		return projectBasicInfoApi.projectBasicInfoPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		tableRef.value.refresh(true)
	}
	// 删除
	const deleteProjectBasicInfo = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		projectBasicInfoApi.projectBasicInfoDelete(params).then(() => {
			tableRef.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchProjectBasicInfo = (params) => {
		projectBasicInfoApi.projectBasicInfoDelete(params).then(() => {
			tableRef.value.clearRefreshSelected()
		})
	}
</script>
