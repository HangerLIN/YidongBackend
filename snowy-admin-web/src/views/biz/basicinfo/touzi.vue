<template>
	<div id="table-wrap">
		<!-- 可编辑表格-Vue3 + ElementPlus -->
		<el-table
			:data="questionChoiceVOlist"
			stripe
			border
			@cell-click="cellClick"
			@row-contextmenu="rightClick"
			:row-class-name="tableRowClassName"
			@header-contextmenu="(column: any, event: MouseEvent) => rightClick(null, column, event)"
		>
			<el-table-column
				type="index"
				label="序号"
				align="center"
				:resizable="false"
				width="70"
			/>

			<template #empty>
				<el-empty description="暂无数据" />
			</template>

			<el-table-column
				:resizable="false"
				align="center"
				v-for="(col, idx) in columnList"
				:key="col.prop"
				:prop="col.prop"
				:label="col.label"
				:index="idx"
			>
				<template #default="{ row }">
<!--					<div-->
<!--						v-if="col.type === 'button'"-->
<!--						style="height: 75px; padding-top: 26px; width: 100%"-->
<!--					>-->
<!--&lt;!&ndash;						<el-badge type="warning" :value="getRiskLenght(row.riskIds)">&ndash;&gt;-->
<!--&lt;!&ndash;							<el-button size="small">&ndash;&gt;-->
<!--&lt;!&ndash;								{{ paramsIdType == 'detail' ? '查看' : '选择' }}&ndash;&gt;-->
<!--&lt;!&ndash;							</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;						</el-badge>&ndash;&gt;-->
<!--					</div>-->
					<el-input-number
						v-if="col.type === 'input-number'"
						v-model.number="row[col.prop]"
						:min="0"
						:max="10"
						:step="0.1"
						:precision="2"
					/>
				</template>
			</el-table-column>
		</el-table>

		<!-- 右键菜单框 -->
		<div v-show="showMenu" id="contextmenu" @mouseleave="showMenu = false">
			<p style="margin-bottom: 10px; text-align: left">列：</p>
			<el-button :icon="CaretTop" @click="addColumn(false)"> 前方插入一列 </el-button>
			<el-button :icon="CaretBottom" @click="addColumn(true)"> 后方插入一列 </el-button>
			<el-button :icon="DeleteFilled" @click="openColumnOrRowSpringFrame('列')">
				删除当前列
			</el-button>
			<el-button @click="renameCol" :icon="EditPen"> 更改列名 </el-button>

			<div style="color: #ccc">-----------------------</div>

			<p style="margin-bottom: 12px">行：</p>
			<el-button :icon="CaretTop" @click="addRow(false)" v-show="!curTarget.isHead">
				上方插入一行
			</el-button>
			<el-button :icon="CaretBottom" @click="addRow(true)" v-show="!curTarget.isHead">
				下方插入一行
			</el-button>
			<el-button :icon="DeleteFilled" @click="addRowHead" v-show="curTarget.isHead">
				下方插入一行
			</el-button>
			<el-button
				:icon="DeleteFilled"
				@click="openColumnOrRowSpringFrame('行')"
				v-show="!curTarget.isHead"
			>
				删除当前行
			</el-button>
		</div>

		<!-- 输入框 -->
		<div v-show="showEditInput" id="editInput">
			<el-input
				ref="iptRef"
				placeholder="请输入内容"
				v-model="curTarget.val"
				clearable
				@change="updTbCellOrHeader"
				@blur="showEditInput = false"
				@keyup="onKeyUp($event)"
			>
				<template #prepend>{{ curColumn.label || curColumn.prop }}</template>
			</el-input>
		</div>

		<!-- 实时数据展示 Start-->
		<!--
		第二个和第三个参数来格式化JSON输出，其中null作为替换函数（这里不进行替换），2表示缩进级别。
		这样JSON数据会以格式化的形式展示，增加了可读性
		 -->
<!--		<div>-->
<!--			<h3 style="text-align: center; margin-top: 15px">实时数据展示</h3>-->
<!--			<label>当前目标：</label>-->
<!--			<pre><code>{{ JSON.stringify(curTarget, null, 2) }}</code></pre>-->
<!--			<div style="width: 100%; height: auto">-->
<!--				<label>表头：</label>-->
<!--				<pre><code v-for="col in columnList" :key="col.prop">{{ JSON.stringify(col, null, 2) }}</code></pre>-->
<!--			</div>-->

<!--			<div>-->
<!--				<label>数据：</label>-->
<!--				<pre><code v-for="(data, idx) in questionChoiceVOlist" :key="idx">-->
<!--					{{ JSON.stringify(data, null, 2) }}-->
<!--				</code></pre>-->
<!--			</div>-->
<!--		</div>-->
		<!-- 实时数据展示 End-->
	</div>
	<el-button @click="con">测试</el-button>
	<el-button @click="con1">测试1</el-button>
</template>

<script setup lang="ts">
import { ref, reactive, computed, toRefs, nextTick } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { DeleteFilled, CaretBottom, CaretTop, EditPen } from '@element-plus/icons-vue';

const props = defineProps({
	cycle: {
		type: String,
		default: "",
	},
})

// Tips： locateMenuOrEditInput 可调整编辑框位置
interface Column {
	prop: string;
	label: string;
	type?: string;
}

interface Data {
	BasicInformation1: string;
	BasicInformation2: string;

	BasicInformation3: string;

	choiceCode: string;
	choiceContent: string;
	// riskIds: string;
	itemScore: string | number;
	[key: string]: unknown;
}

interface Target {
	rowIdx: number | null;
	colIdx: number | null;
	val: string | null;
	isHead: boolean | undefined;
}

// 接收addEdit父组件传过来的数据，用于判断是新增、编辑、详情页面
const paramsIdType = 'detail';

// const cycle='1';

const state = reactive({
	columnList: [
		{ prop: 'BasicInformation1', label: '产品代码' },
		{ prop: 'BasicInformation2', label: '产品名称' },
		{ prop: 'BasicInformation3', label: '规格型号', type: 'button' },
		{ prop: 'itemScore', label: '合计', type: 'input-number' },
	] as Column[],
	questionChoiceVOlist: [
		{
			BasicInformation1: '310101',
			BasicInformation2: '双向双车道',
			BasicInformation3: '有绿植',
			itemScore: 1,
			isClickCheckBtn: true,
			// riskIds: '46',
			id: 1,
		},
		{
			BasicInformation1: '310102',
			BasicInformation2: '双向四车道',
			BasicInformation3: '有绿植',
			choiceCode: 'B',
			choiceContent: '否',
			// riskIds: '46',
			itemScore: 4,
			isClickCheckBtn: true,
			id: 2,
		},
		{
			BasicInformation1: '310103',
			BasicInformation2: '双向八车道',
			BasicInformation3: '有绿植',
			choiceCode: 'C',
			choiceContent: '否',
			// riskIds: '',
			itemScore: 4,
			isClickCheckBtn: true,
			id: 3,
		},
	] as Data[],
	showMenu: false, // 显示右键菜单
	showEditInput: false, // 显示单元格/表头内容编辑输入框
	curTarget: {
		// 当前目标信息
		rowIdx: null, // 行下标
		colIdx: null, // 列下标
		val: null, // 单元格内容/列名
		isHead: undefined, // 当前目标是表头？
	} as Target,
	countCol: 0, // 新建列计数
});
const iptRef = ref();

const { columnList, questionChoiceVOlist, showMenu, showEditInput, curTarget } = toRefs(state);

// 当前列
const curColumn = computed(() => {
	return curTarget.value.colIdx !== null
		? columnList.value[curTarget.value.colIdx]
		: { prop: '', label: '' };
});

// 计算风险点数量
// const getRiskLenght = computed(() => {
// 	return (riskIds: string) => riskIds.split(',').filter(Boolean).length;
// });

/**
 * 删除列/行
 * @method  delColumn
 * @param {  string }  type - '列' | '行'
 **/
const openColumnOrRowSpringFrame = (type: string) => {
	ElMessageBox.confirm(`此操作将永久删除该${type === '列' ? '列' : '行'}, 是否继续 ?, '提示'`, {
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		type: 'warning',
	})
		.then(() => {
			if (type === '列') {
				delColumn();
			} else if (type === '行') delRow();

			ElMessage.success('删除成功');
		})
		.catch(() => ElMessage.info('已取消删除'));
};

// 回车键关闭编辑框
const onKeyUp = (e: KeyboardEvent) => {
	if (e.key === 'Enter') {
		showEditInput.value = false;
	}
};

// 控制某字段不能打开弹框
const isPop = (column: { label: string }) => {
	return column.label === '风险点' || column.label === '选项分值';
};

// 左键输入框
const cellClick = (
	row: { [x: string]: any; row_index: any },
	column: { index: null; property: string | number; label: string },
	_cell: any,
	event: MouseEvent
) => {
	// 如果是风险点或选项分值，不执行后续代码
	if (isPop(column)) return;

	iptRef.value.focus();
	if (column.index == null) return;
	locateMenuOrEditInput('editInput', -300, event); // 左键输入框定位 Y
	showEditInput.value = true;
	iptRef.value.focus();

	// 当前目标
	curTarget.value = {
		rowIdx: row.row_index,
		colIdx: column.index,
		val: row[column.property],
		isHead: false,
	};
};

// 表头右击事件 - 打开菜单
const rightClick = (row: any, column: any, event: MouseEvent) => {
	event.preventDefault();

	if (column.index == null) return;
	// 如果tableData有数据并且当前目标是表头，那么就返回，不执行后续操作
	// if (questionChoiceVOlist.value.length > 0 && !row) return;
	if (isPop(column)) return;

	showMenu.value = false;
	locateMenuOrEditInput('contextmenu', -500, event); // 右键输入框

	showMenu.value = true;
	curTarget.value = {
		rowIdx: row ? row.row_index : null,
		colIdx: column.index,
		val: row ? row[column.property] : column.label,
		isHead: !row,
	};
};

// 更改列名
const renameCol = () => {
	showEditInput.value = false;
	if (curTarget.value.colIdx === null) return;
	showEditInput.value = true;
	nextTick(() => {
		iptRef.value.focus();
	});
};

// 更改单元格内容/列名
const updTbCellOrHeader = (val: string) => {
	if (!curTarget.value.isHead) {
		if (curTarget.value.rowIdx !== null) {
			(questionChoiceVOlist.value[curTarget.value.rowIdx] as Data)[curColumn.value.prop] =
				val;
		}
	} else {
		if (!val) return;
		if (curTarget.value.colIdx !== null) {
			columnList.value[curTarget.value.colIdx].label = val;
		}
	}
};
// 新增行
const addRow = (later: boolean) => {
	showMenu.value = false;
	const idx = later ? curTarget.value.rowIdx! + 1 : curTarget.value.rowIdx!;
	let obj: any = {};
	columnList.value.forEach((p) => obj[p.prop]);
	questionChoiceVOlist.value.splice(idx, 0, obj);
	// 设置新增行数据默认值
	questionChoiceVOlist.value[idx] = {
		BasicInformation1: '',
		BasicInformation2: '',
		BasicInformation3: '',
		choiceCode: '',
		choiceContent: '',
		// riskIds: '',
		itemScore: 0,
		id: Math.floor(Math.random() * 100000),
	};
};

// 表头下新增行
const addRowHead = () => {
	showMenu.value = false;
	let obj: any = {};
	columnList.value.forEach((p) => obj[p.prop]);
	questionChoiceVOlist.value.unshift(obj);
	questionChoiceVOlist.value[0] = {
		BasicInformation1: '',
		BasicInformation2: '',
		BasicInformation3: '',
		choiceCode: '',
		choiceContent: '',
		// riskIds: '',
		itemScore: 0,
		id: Math.floor(Math.random() * 100000),
	};
};
// 删除行
const delRow = () => {
	showMenu.value = false;
	curTarget.value.rowIdx !== null &&
	questionChoiceVOlist.value.splice(curTarget.value.rowIdx!, 1);
};

// 新增列
const addColumn = (later: boolean) => {
	showMenu.value = false;
	const idx = later ? curTarget.value.colIdx! + 1 : curTarget.value.colIdx!;
	const colStr = { prop: 'Zk-NewCol - ' + ++state.countCol, label: '' };
	columnList.value.splice(idx, 0, colStr);
	questionChoiceVOlist.value.forEach((p) => (p[colStr.prop] = ''));
};

// 删除列
const delColumn = () => {
	showMenu.value = false;
	questionChoiceVOlist.value.forEach((p) => {
		delete p[curColumn.value.prop];
	});
	columnList.value.splice(curTarget.value.colIdx!, 1);
};

// 添加表格行下标
const tableRowClassName = ({ row, rowIndex }: { row: any; rowIndex: number }) => {
	row.row_index = rowIndex;
};

// 定位菜单/编辑框
const locateMenuOrEditInput = (eleId: string, distance: number, event: MouseEvent) => {
	if (window.innerWidth < 1130 || window.innerWidth < 660)
		return ElMessage.warning('窗口太小，已经固定菜单位置，或请重新调整窗口大小');
	const ele = document.getElementById(eleId) as HTMLElement;
	const x = event.pageX;
	const y = event.clientY + 200; //右键菜单位置 Y
	let left = x + distance + 200; //右键菜单位置 X
	let top;
	if (eleId == 'editInput') {
		// 左键
		top = y + distance;
		left = x + distance - 120;
	} else {
		// 右键
		top = y + distance + 170;
	}
	ele.style.left = `${left}px`;
	ele.style.top = `${top}px`;
};
// 插入四列
// 定义一个函数来按周期添加年份列

const addYearColumns = () => {

	showMenu.value = false; // 关闭菜单（如果需要）



	for (let i = 0; i < 5; i++) {

		// 计算年份（从当前年份开始，每次循环递增5年）

		const year = 2024 + i ;



		// 创建新列的配置对象（这里我们使用年份作为prop）

		const colStr = {

			prop: `year-${year}`, // 使用年份作为prop的一部分，以便唯一标识

			label: `${year}年` // 列的显示标签

		};



		// 向列列表中添加新列

		columnList.value.splice(columnList.value.length-1, 0, colStr);



		// 初始化新列的数据（假设questionChoiceVOlist的每个对象都需要这个新属性）

		questionChoiceVOlist.value.forEach(p => {

			// 如果p对象之前没有这个属性，则添加它并设置为空字符串或null等

			// 但在这个例子中，我们直接设置年份（或空字符串，如果你想要）

			// 注意：这里可能需要根据实际情况调整，因为通常你不会在数据行中存储年份列的值

			// 除非这是一个特殊的用例，比如需要记录每行数据的“创建年份”或类似的东西

			// 但在这个例子中，我们只是为了演示如何添加列

			p[colStr.prop] = ''; // 或者你可以设置为year，但这可能不是你想要的行为

		});



		// 注意：在实际应用中，你可能不需要在数据行的每个对象中设置这个新属性

		// 除非你有特定的理由要这样做（比如用于筛选、显示等）

	}



	// 如果你只是想递增countCol来追踪列的数量（尽管在这个例子中我们没有直接使用它）

	// state.countCol += cycles;

};


//测试
const con = () =>{
	addYearColumns()
	console.log(questionChoiceVOlist.value)
	console.log(props.cycle)
}
const con1 = () =>{
	console.log(questionChoiceVOlist.value)
	console.log(props.cycle)
}

defineExpose({
	questionChoiceVOlist,
});
</script>

<style lang="scss" scoped>
#table-wrap {
	width: 100%;
	height: 100%;
	/* 左键 */
	#contextmenu {
		position: fixed;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		z-index: 999999;
		top: 0;
		left: 0;
		height: auto;
		width: 200px;
		border-radius: 3px;
		border: #e2e2e2 1px solid;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
		background-color: #fff;
		border-radius: 6px;
		padding: 15px 10px 14px 12px;

		button {
			display: block;
			margin: 0 0 5px;
		}
	}
	/* 右键 */
	#editInput {
		position: fixed;
		top: 0;
		left: 0;
		z-index: 999999;
	}
	/* 实时数据 */

	pre {
		border: 1px solid #cccccc;
		padding: 10px;
		overflow: auto;
	}
}
</style>
