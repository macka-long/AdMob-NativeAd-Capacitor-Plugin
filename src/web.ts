import { WebPlugin } from '@capacitor/core';

import type { NativeAdPluginPlugin } from './definitions';

export class NativeAdPluginWeb extends WebPlugin implements NativeAdPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
