package com.patch;

import dev.kilt.KiltAPI;
import dev.kilt.loader.mod.Mod;
import dev.kilt.loader.mod.ModRepository;
import net.fabricmc.api.ModInitializer;

import java.util.Optional;

public class KiltTaczSpoof implements ModInitializer {
    @Override
    public void onInitialize() {
        // 获取Kilt专属的Forge模组仓库
        ModRepository forgeRepo = KiltAPI.getForgeModRepository();

        // 读取原本的TaCZ重织模组
        Optional<Mod> originalTacz = forgeRepo.getMod("tacz");

        if (originalTacz.isPresent()) {
            // 包装原模组，仅重写对外版本号
            Mod spoofedTacz = new ModWrapper(originalTacz.get()) {
                @Override
                public String getVersion() {
                    // 对外欺骗Kilt：版本号强制 1.1.7
                    return "1.1.7";
                }
            };

            // 将伪造版本写入Kilt仓库，覆盖校验数据
            forgeRepo.replaceMod("tacz", spoofedTacz);
        }
    }
}
