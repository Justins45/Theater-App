import { useRoute } from 'vue-router'
import { watch } from 'vue'

export function useRouteData<T extends string>(params: T[], callback: (values: Record<T, string>) => void) {
  const route = useRoute()

  watch(
    () => params.map(p => route.params[p] as string),
    (values) => callback(Object.fromEntries(params.map((p, i) => [p, values[i]])) as Record<T, string>),
    { immediate: true }
  )
}
