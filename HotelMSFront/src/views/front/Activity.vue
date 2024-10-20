<template>
  <div class="main-content">

    <ActivityList :span="6"/>
    <Footer></Footer>
  </div>
</template>

<script>
import Footer from "@/components/Footer.vue";
import ActivityList from "@/components/ActivityList.vue";
export default {
  name: "Activity",
  data() {
    return {
    }
  },
  components: {
    Footer,
    ActivityList
  },
  mounted() {
  },
  methods: {
  }
}
</script>
