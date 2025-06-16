import { registerPlugin } from '@capacitor/core';

import type { NativeAdPluginPlugin } from './definitions';

const NativeAdPlugin = registerPlugin<NativeAdPluginPlugin>('NativeAdPlugin', {
  web: () => import('./web').then((m) => new m.NativeAdPluginWeb()),
});

export * from './definitions';
export { NativeAdPlugin };
