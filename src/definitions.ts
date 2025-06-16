export interface NativeAdPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
