<template>
  <div class="search-song-list">
    <play-list :playList="playList" path="song-sheet-detail"></play-list>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, watch, onMounted, getCurrentInstance } from "vue";
import { useStore } from "vuex";
import PlayList from "@/components/PlayList.vue";
import { HttpManager } from "@/api";

export default defineComponent({
  components: {
    PlayList,
  },
  setup() {
    const { proxy } = getCurrentInstance();
    const store = useStore();

    const playList = ref([]);
    const searchWord = computed(() => store.getters.searchWord);
    watch(searchWord, (value) => {
      getSearchList(value);
    });

    async function getSearchList(value) {
      if (!value) return;
      const result = (await HttpManager.getSongListOfSearch(value)) as ResponseBody;
      playList.value = result.data
    }

    onMounted(() => {
      getSearchList(proxy.$route.query.keywords);
    });

    return {
      playList,
    };
  },
});
</script>
