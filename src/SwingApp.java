import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import utility.Constants;

// Copyright 2022 Kyle King
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

public class SwingApp extends JFrame{
    private static void createAndShowGui() {
        SwingApp app = new SwingApp();
        app.setPreferredSize(Constants.PREF_SCREEN_SIZE);
        app.setMinimumSize(Constants.PREF_SCREEN_SIZE);
        app.setMaximumSize(Constants.PREF_SCREEN_SIZE);
        app.setTitle("JPong");
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Game game = new Game();
        app.add(game);
        game.setPreferredSize(Constants.PREF_SCREEN_SIZE);
        app.pack();
        app.setVisible(true);
        app.setLocationRelativeTo(null);
        app.setResizable(false);
    }
    /**
     * Driver
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->SwingApp.createAndShowGui());
    }
}