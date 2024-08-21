// vite.config.mjs
import { resolve } from "path";
import { defineConfig, loadEnv } from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/vite/dist/node/index.js";
import vue from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import Components from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/unplugin-vue-components/dist/vite.js";
import VueJSX from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";
import AutoImport from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/unplugin-auto-import/dist/vite.js";
import vueSetupExtend from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/vite-plugin-vue-setup-extend/dist/index.mjs";
import { visualizer } from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/rollup-plugin-visualizer/dist/plugin/index.js";
import Less2CssVariablePlugin from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/antd-less-to-css-variable/dist/index.js";
import viteCompression from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/vite-plugin-compression/dist/index.mjs";
import { theme } from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/ant-design-vue/lib/index.js";
import convertLegacyToken from "file:///F:/DMLL/IdeaProjects/YidongBackend/snowy-admin-web/node_modules/ant-design-vue/lib/theme/convertLegacyToken.js";
var __vite_injected_original_dirname = "F:\\DMLL\\IdeaProjects\\YidongBackend\\snowy-admin-web";
var { defaultAlgorithm, defaultSeed } = theme;
var mapToken = defaultAlgorithm(defaultSeed);
var v3Token = convertLegacyToken.default(mapToken);
var r = (...args) => resolve(__vite_injected_original_dirname, ".", ...args);
var vite_config_default = defineConfig(({ command, mode }) => {
  const envConfig = loadEnv(mode, "./");
  const alias = {
    "~": `${resolve(__vite_injected_original_dirname, "./")}`,
    "@/": `${resolve(__vite_injected_original_dirname, "src")}/`
  };
  return {
    server: {
      port: envConfig.VITE_PORT,
      proxy: {
        "/api": {
          target: envConfig.VITE_API_BASEURL,
          ws: false,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, "")
        }
      }
    },
    resolve: {
      alias
    },
    // 解决警告You are running the esm-bundler build of vue-i18n.
    define: {
      __VUE_I18N_FULL_INSTALL__: true,
      __VUE_I18N_LEGACY_API__: true,
      __VUE_I18N_PROD_DEVTOOLS__: true,
      __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: true
    },
    build: {
      // sourcemap: true,
      manifest: true,
      brotliSize: false,
      rollupOptions: {
        output: {
          manualChunks: {
            echarts: ["echarts"],
            "ant-design-vue": ["ant-design-vue"],
            vue: ["vue", "vue-router", "pinia", "vue-i18n"]
          }
        }
      },
      chunkSizeWarningLimit: 1e3
    },
    plugins: [
      vue({
        script: {
          refTransform: true
        }
      }),
      viteCompression(),
      vueSetupExtend(),
      VueJSX(),
      AutoImport({
        imports: ["vue"],
        dirs: ["./src/utils/permission"],
        dts: r("src/auto-imports.d.ts")
      }),
      // 组件按需引入
      Components({
        dirs: [r("src/components")],
        dts: false,
        resolvers: []
      }),
      visualizer()
    ],
    css: {
      preprocessorOptions: {
        less: {
          javascriptEnabled: true,
          plugins: [new Less2CssVariablePlugin({
            // TODO：有必要用的情况下，是否需要传入 variables，可能会造成重复引用
            variables: { ...v3Token }
          })],
          modifyVars: v3Token
        }
      }
    },
    optimizeDeps: {}
  };
});
export {
  vite_config_default as default,
  r
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcubWpzIl0sCiAgInNvdXJjZXNDb250ZW50IjogWyJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRjpcXFxcRE1MTFxcXFxJZGVhUHJvamVjdHNcXFxcWWlkb25nQmFja2VuZFxcXFxzbm93eS1hZG1pbi13ZWJcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkY6XFxcXERNTExcXFxcSWRlYVByb2plY3RzXFxcXFlpZG9uZ0JhY2tlbmRcXFxcc25vd3ktYWRtaW4td2ViXFxcXHZpdGUuY29uZmlnLm1qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRjovRE1MTC9JZGVhUHJvamVjdHMvWWlkb25nQmFja2VuZC9zbm93eS1hZG1pbi13ZWIvdml0ZS5jb25maWcubWpzXCI7LyoqXHJcbiAqICBDb3B5cmlnaHQgWzIwMjJdIFtodHRwczovL3d3dy54aWFvbnVvLnZpcF1cclxuICpcdFNub3d5XHU5MUM3XHU3NTI4QVBBQ0hFIExJQ0VOU0UgMi4wXHU1RjAwXHU2RTkwXHU1MzRGXHU4QkFFXHVGRjBDXHU2MEE4XHU1NzI4XHU0RjdGXHU3NTI4XHU4RkM3XHU3QTBCXHU0RTJEXHVGRjBDXHU5NzAwXHU4OTgxXHU2Q0U4XHU2MTBGXHU0RUU1XHU0RTBCXHU1MUUwXHU3MEI5XHVGRjFBXHJcbiAqXHQxLlx1OEJGN1x1NEUwRFx1ODk4MVx1NTIyMFx1OTY2NFx1NTQ4Q1x1NEZFRVx1NjUzOVx1NjgzOVx1NzZFRVx1NUY1NVx1NEUwQlx1NzY4NExJQ0VOU0VcdTY1ODdcdTRFRjZcdTMwMDJcclxuICpcdDIuXHU4QkY3XHU0RTBEXHU4OTgxXHU1MjIwXHU5NjY0XHU1NDhDXHU0RkVFXHU2NTM5U25vd3lcdTZFOTBcdTc4MDFcdTU5MzRcdTkwRThcdTc2ODRcdTcyNDhcdTY3NDNcdTU4RjBcdTY2MEVcdTMwMDJcclxuICpcdDMuXHU2NzJDXHU5ODc5XHU3NkVFXHU0RUUzXHU3ODAxXHU1M0VGXHU1MTREXHU4RDM5XHU1NTQ2XHU0RTFBXHU0RjdGXHU3NTI4XHVGRjBDXHU1NTQ2XHU0RTFBXHU0RjdGXHU3NTI4XHU4QkY3XHU0RkREXHU3NTU5XHU2RTkwXHU3ODAxXHU1NDhDXHU3NkY4XHU1MTczXHU2M0NGXHU4RkYwXHU2NTg3XHU0RUY2XHU3Njg0XHU5ODc5XHU3NkVFXHU1MUZBXHU1OTA0XHVGRjBDXHU0RjVDXHU4MDA1XHU1OEYwXHU2NjBFXHU3QjQ5XHUzMDAyXHJcbiAqXHQ0Llx1NTIwNlx1NTNEMVx1NkU5MFx1NzgwMVx1NjVGNlx1NTAxOVx1RkYwQ1x1OEJGN1x1NkNFOFx1NjYwRVx1OEY2Rlx1NEVGNlx1NTFGQVx1NTkwNCBodHRwczovL3d3dy54aWFvbnVvLnZpcFxyXG4gKlx0NS5cdTRFMERcdTUzRUZcdTRFOENcdTZCMjFcdTUyMDZcdTUzRDFcdTVGMDBcdTZFOTBcdTUzQzJcdTRFMEVcdTU0MENcdTdDN0JcdTdBREVcdTU0QzFcdUZGMENcdTU5ODJcdTY3MDlcdTYwRjNcdTZDRDVcdTUzRUZcdTgwNTRcdTdDRkJcdTU2RTJcdTk2MUZ4aWFvbnVvYmFzZUBxcS5jb21cdTU1NDZcdThCQUVcdTU0MDhcdTRGNUNcdTMwMDJcclxuICpcdDYuXHU4MkU1XHU2MEE4XHU3Njg0XHU5ODc5XHU3NkVFXHU2NUUwXHU2Q0Q1XHU2RUUxXHU4REIzXHU0RUU1XHU0RTBBXHU1MUUwXHU3MEI5XHVGRjBDXHU5NzAwXHU4OTgxXHU2NkY0XHU1OTFBXHU1MjlGXHU4MEZEXHU0RUUzXHU3ODAxXHVGRjBDXHU4M0I3XHU1M0Q2U25vd3lcdTU1NDZcdTRFMUFcdTYzODhcdTY3NDNcdThCQjhcdTUzRUZcdUZGMENcdThCRjdcdTU3MjhcdTVCOThcdTdGNTFcdThEMkRcdTRFNzBcdTYzODhcdTY3NDNcdUZGMENcdTU3MzBcdTU3NDBcdTRFM0EgaHR0cHM6Ly93d3cueGlhb251by52aXBcclxuICovXHJcbmltcG9ydCB7IHJlc29sdmUgfSBmcm9tICdwYXRoJ1xyXG5pbXBvcnQgeyBkZWZpbmVDb25maWcsIGxvYWRFbnYgfSBmcm9tICd2aXRlJ1xyXG5pbXBvcnQgdnVlIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZSdcclxuaW1wb3J0IENvbXBvbmVudHMgZnJvbSAndW5wbHVnaW4tdnVlLWNvbXBvbmVudHMvdml0ZSdcclxuaW1wb3J0IFZ1ZUpTWCBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUtanN4J1xyXG5pbXBvcnQgQXV0b0ltcG9ydCBmcm9tICd1bnBsdWdpbi1hdXRvLWltcG9ydC92aXRlJ1xyXG5pbXBvcnQgdnVlU2V0dXBFeHRlbmQgZnJvbSAndml0ZS1wbHVnaW4tdnVlLXNldHVwLWV4dGVuZCdcclxuaW1wb3J0IHsgdmlzdWFsaXplciB9IGZyb20gJ3JvbGx1cC1wbHVnaW4tdmlzdWFsaXplcidcclxuaW1wb3J0IExlc3MyQ3NzVmFyaWFibGVQbHVnaW4gZnJvbSAnYW50ZC1sZXNzLXRvLWNzcy12YXJpYWJsZSdcclxuaW1wb3J0IHZpdGVDb21wcmVzc2lvbiBmcm9tICd2aXRlLXBsdWdpbi1jb21wcmVzc2lvbidcclxuXHJcbi8vICBhbnQtZGVzaWduLXZ1ZSBcdTc2ODQgbGVzcyBcdTUzRDhcdTkxQ0ZcdUZGMENcdTkwMUFcdThGQzdcdTUxN0NcdTVCQjlcdTUzMDVcdTVDMDYgdjQgXHU1M0Q4XHU5MUNGXHU4RjZDXHU4QkQxXHU2MjEwIHYzIFx1NzI0OFx1NjcyQ1x1RkYwQ1x1NUU3Nlx1OTAxQVx1OEZDNyBsZXNzLWxvYWRlciBcdTZDRThcdTUxNjVcclxuaW1wb3J0IHsgdGhlbWUgfSBmcm9tICdhbnQtZGVzaWduLXZ1ZS9saWInO1xyXG5pbXBvcnQgY29udmVydExlZ2FjeVRva2VuIGZyb20gJ2FudC1kZXNpZ24tdnVlL2xpYi90aGVtZS9jb252ZXJ0TGVnYWN5VG9rZW4nO1xyXG5jb25zdCB7IGRlZmF1bHRBbGdvcml0aG0sIGRlZmF1bHRTZWVkIH0gPSB0aGVtZTtcclxuY29uc3QgbWFwVG9rZW4gPSBkZWZhdWx0QWxnb3JpdGhtKGRlZmF1bHRTZWVkKTtcclxuY29uc3QgdjNUb2tlbiA9IGNvbnZlcnRMZWdhY3lUb2tlbi5kZWZhdWx0KG1hcFRva2VuKTtcclxuXHJcbmV4cG9ydCBjb25zdCByID0gKC4uLmFyZ3MpID0+IHJlc29sdmUoX19kaXJuYW1lLCAnLicsIC4uLmFyZ3MpXHJcblxyXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWcoKHsgY29tbWFuZCwgbW9kZSB9KSA9PiB7XHJcblx0Y29uc3QgZW52Q29uZmlnID0gbG9hZEVudihtb2RlLCAnLi8nKVxyXG5cdGNvbnN0IGFsaWFzID0ge1xyXG5cdFx0J34nOiBgJHtyZXNvbHZlKF9fZGlybmFtZSwgJy4vJyl9YCxcclxuXHRcdCdALyc6IGAke3Jlc29sdmUoX19kaXJuYW1lLCAnc3JjJyl9L2BcclxuXHR9XHJcblx0cmV0dXJuIHtcclxuXHRcdHNlcnZlcjoge1xyXG5cdFx0XHRwb3J0OiBlbnZDb25maWcuVklURV9QT1JULFxyXG5cdFx0XHRwcm94eToge1xyXG5cdFx0XHRcdCcvYXBpJzoge1xyXG5cdFx0XHRcdFx0dGFyZ2V0OiBlbnZDb25maWcuVklURV9BUElfQkFTRVVSTCxcclxuXHRcdFx0XHRcdHdzOiBmYWxzZSxcclxuXHRcdFx0XHRcdGNoYW5nZU9yaWdpbjogdHJ1ZSxcclxuXHRcdFx0XHRcdHJld3JpdGU6IChwYXRoKSA9PiBwYXRoLnJlcGxhY2UoL15cXC9hcGkvLCAnJylcclxuXHRcdFx0XHR9XHJcblx0XHRcdH1cclxuXHRcdH0sXHJcblx0XHRyZXNvbHZlOiB7XHJcblx0XHRcdGFsaWFzXHJcblx0XHR9LFxyXG5cdFx0Ly8gXHU4OUUzXHU1MUIzXHU4QjY2XHU1NDRBWW91IGFyZSBydW5uaW5nIHRoZSBlc20tYnVuZGxlciBidWlsZCBvZiB2dWUtaTE4bi5cclxuXHRcdGRlZmluZToge1xyXG5cdFx0XHRfX1ZVRV9JMThOX0ZVTExfSU5TVEFMTF9fOiB0cnVlLFxyXG5cdFx0XHRfX1ZVRV9JMThOX0xFR0FDWV9BUElfXzogdHJ1ZSxcclxuXHRcdFx0X19WVUVfSTE4Tl9QUk9EX0RFVlRPT0xTX186IHRydWUsXHJcblx0XHRcdF9fVlVFX1BST0RfSFlEUkFUSU9OX01JU01BVENIX0RFVEFJTFNfXzogdHJ1ZVxyXG5cdFx0fSxcclxuXHRcdGJ1aWxkOiB7XHJcblx0XHRcdC8vIHNvdXJjZW1hcDogdHJ1ZSxcclxuXHRcdFx0bWFuaWZlc3Q6IHRydWUsXHJcblx0XHRcdGJyb3RsaVNpemU6IGZhbHNlLFxyXG5cdFx0XHRyb2xsdXBPcHRpb25zOiB7XHJcblx0XHRcdFx0b3V0cHV0OiB7XHJcblx0XHRcdFx0XHRtYW51YWxDaHVua3M6IHtcclxuXHRcdFx0XHRcdFx0ZWNoYXJ0czogWydlY2hhcnRzJ10sXHJcblx0XHRcdFx0XHRcdCdhbnQtZGVzaWduLXZ1ZSc6IFsnYW50LWRlc2lnbi12dWUnXSxcclxuXHRcdFx0XHRcdFx0dnVlOiBbJ3Z1ZScsICd2dWUtcm91dGVyJywgJ3BpbmlhJywgJ3Z1ZS1pMThuJ11cclxuXHRcdFx0XHRcdH1cclxuXHRcdFx0XHR9XHJcblx0XHRcdH0sXHJcblx0XHRcdGNodW5rU2l6ZVdhcm5pbmdMaW1pdDogMTAwMFxyXG5cdFx0fSxcclxuXHRcdHBsdWdpbnM6IFtcclxuXHRcdFx0dnVlKHtcclxuXHRcdFx0XHRzY3JpcHQ6IHtcclxuXHRcdFx0XHRcdHJlZlRyYW5zZm9ybTogdHJ1ZVxyXG5cdFx0XHRcdH1cclxuXHRcdFx0fSksXHJcblx0XHRcdHZpdGVDb21wcmVzc2lvbigpLFxyXG5cdFx0XHR2dWVTZXR1cEV4dGVuZCgpLFxyXG5cdFx0XHRWdWVKU1goKSxcclxuXHRcdFx0QXV0b0ltcG9ydCh7XHJcblx0XHRcdFx0aW1wb3J0czogWyd2dWUnXSxcclxuXHRcdFx0XHRkaXJzOiBbJy4vc3JjL3V0aWxzL3Blcm1pc3Npb24nXSxcclxuXHRcdFx0XHRkdHM6IHIoJ3NyYy9hdXRvLWltcG9ydHMuZC50cycpXHJcblx0XHRcdH0pLFxyXG5cdFx0XHQvLyBcdTdFQzRcdTRFRjZcdTYzMDlcdTk3MDBcdTVGMTVcdTUxNjVcclxuXHRcdFx0Q29tcG9uZW50cyh7XHJcblx0XHRcdFx0ZGlyczogW3IoJ3NyYy9jb21wb25lbnRzJyldLFxyXG5cdFx0XHRcdGR0czogZmFsc2UsXHJcblx0XHRcdFx0cmVzb2x2ZXJzOiBbXVxyXG5cdFx0XHR9KSxcclxuXHRcdFx0dmlzdWFsaXplcigpXHJcblx0XHRdLFxyXG5cdFx0Y3NzOiB7XHJcblx0XHRcdHByZXByb2Nlc3Nvck9wdGlvbnM6IHtcclxuXHRcdFx0XHRsZXNzOiB7XHJcblx0XHRcdFx0XHRqYXZhc2NyaXB0RW5hYmxlZDogdHJ1ZSxcclxuXHRcdFx0XHRcdHBsdWdpbnM6IFtuZXcgTGVzczJDc3NWYXJpYWJsZVBsdWdpbih7XHJcblx0XHRcdFx0XHRcdC8vIFRPRE9cdUZGMUFcdTY3MDlcdTVGQzVcdTg5ODFcdTc1MjhcdTc2ODRcdTYwQzVcdTUxQjVcdTRFMEJcdUZGMENcdTY2MkZcdTU0MjZcdTk3MDBcdTg5ODFcdTRGMjBcdTUxNjUgdmFyaWFibGVzXHVGRjBDXHU1M0VGXHU4MEZEXHU0RjFBXHU5MDIwXHU2MjEwXHU5MUNEXHU1OTBEXHU1RjE1XHU3NTI4XHJcblx0XHRcdFx0XHRcdHZhcmlhYmxlczogeyAuLi52M1Rva2VuIH1cclxuXHRcdFx0XHRcdH0pXSxcclxuXHRcdFx0XHRcdG1vZGlmeVZhcnM6IHYzVG9rZW5cclxuXHRcdFx0XHR9XHJcblx0XHRcdH1cclxuXHRcdH0sXHJcblx0XHRvcHRpbWl6ZURlcHM6IHt9XHJcblx0fVxyXG59KVxyXG4iXSwKICAibWFwcGluZ3MiOiAiO0FBVUEsU0FBUyxlQUFlO0FBQ3hCLFNBQVMsY0FBYyxlQUFlO0FBQ3RDLE9BQU8sU0FBUztBQUNoQixPQUFPLGdCQUFnQjtBQUN2QixPQUFPLFlBQVk7QUFDbkIsT0FBTyxnQkFBZ0I7QUFDdkIsT0FBTyxvQkFBb0I7QUFDM0IsU0FBUyxrQkFBa0I7QUFDM0IsT0FBTyw0QkFBNEI7QUFDbkMsT0FBTyxxQkFBcUI7QUFHNUIsU0FBUyxhQUFhO0FB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
