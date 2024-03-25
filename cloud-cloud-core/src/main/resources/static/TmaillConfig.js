Vue.component('tmall-config', {
    template: `
    <div>
      <h2>天猫精灵配置页面</h2>
      <button @click="testCl">asd</button>
      <div class="tab-content">天猫精灵配置内容</div>
    </div>
  `,
    methods: {
        testCl() {
            console.log("1")
        }
    },
    data: function () {
    }
});