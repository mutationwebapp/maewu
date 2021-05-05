export type { WindowConfig, WindowPosition, TilesLayout, PlatformName } from './types'
export type { ExternalWindow } from './externalWindowDefault'

export { InteropTopics } from './types'
export * from './platformWindow'
export * from './platform'
export * from './getPlatformAsync'
export { PlatformProvider, usePlatform } from './context'
export { externalWindowDefault } from './externalWindowDefault'
export * from './excelApp'
export * from './limitChecker'
export { isParentAppOpenfinLauncher } from './openfin/adapter/launcherUtils'
export { isRunningInIE } from './helpers'
