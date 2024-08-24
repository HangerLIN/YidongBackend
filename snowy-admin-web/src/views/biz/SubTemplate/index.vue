<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="子模版名称" name="subtemplateName">
						<a-input v-model:value="searchFormState.subtemplateName" placeholder="请输入子模版名称" />
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
			:row-key="(record) => record.subtemplateId"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('subtemplateAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('subtemplateBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchSubtemplate"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('subtemplateEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['subtemplateEdit', 'subtemplateDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteSubtemplate(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('subtemplateDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="tableRef.refresh()" />
</template>

<script setup name="SubTemplate">
	import { cloneDeep } from 'lodash-es'
	import Form from './form.vue'
	import subtemplateApi from '@/api/biz/subtemplateApi'
	const searchFormState = ref({})
	const searchFormRef = ref()
	const tableRef = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '模版ID',
			dataIndex: 'templateId'
		},
		{
			title: '子模版序号',
			dataIndex: 'subtemplateSerial'
		},
		{
			title: '子模版名称',
			dataIndex: 'subtemplateName'
		},
		{
			title: '子模版类型（0:涉及计算，1：不涉及计算，2:可涉及计算也可不涉及）',
			dataIndex: 'templateType'
		},
		{
			title: '基础信息',
			dataIndex: 'basicInformation'
		},
		{
			title: '开始年份公式',
			dataIndex: 'startyearEq'
		},
		{
			title: '其他年份公式',
			dataIndex: 'endyearEq'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['subtemplateEdit', 'subtemplateDelete'])) {
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
		return subtemplateApi.subtemplatePage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		tableRef.value.refresh(true)
	}
	// 删除
	const deleteSubtemplate = (record) => {
		let params = [
			{
				subtemplateId: record.subtemplateId
			}
		]
		subtemplateApi.subtemplateDelete(params).then(() => {
			tableRef.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchSubtemplate = (params) => {
		subtemplateApi.subtemplateDelete(params).then(() => {
			tableRef.value.clearRefreshSelected()
		})
	}
</script>
