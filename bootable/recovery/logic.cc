/**
 * Copyright 2025 Chagas Inc.
 * Copyright 2025 Chagas LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * This file contains components derived from the Android Open Source Project.
 * Copyright (C) The Android Open Source Project
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <ctype.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mount.h>
#include <linux/input.h>
#include "minui/minui.h"
#include "bootloader_message/bootloader_message.h"
#include "otautil/error_code.h"
#include "otautil/mounts.h"
#include "recovery_ui.h"
#include "roots.h"
#include "recovery_utils.h"
#include <minui/minui.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



if (key == KEY_VOLUMEUP)
    move_up();

if (key == KEY_VOLUMEDOWN)
    move_down();

if (key == KEY_POWER)
    select();
void draw_menu(int selected) {
    gr_clear();  // limpa a tela

    const char* items[] = {
        "Reboot system now",
        "Wipe data/factory reset",
        "Wipe cache",
        "Power off"
    };

    int base_y = 100;

    for (int i = 0; i < 4; i++) {
        int y = base_y + i * 40;

        if (i == selected) {
            // fundo azul claro
            gr_color(0, 70, 170, 255);
            gr_fill(0, y - 5, gr_fb_width(), y + 35);

            // texto azul forte
            gr_color(0, 180, 255, 255);
        } else {
            // texto branco
            gr_color(255, 255, 255, 255);
        }

        gr_text(20, y, items[i], 0);
    }

    gr_flip(); // atualiza a tela
}
