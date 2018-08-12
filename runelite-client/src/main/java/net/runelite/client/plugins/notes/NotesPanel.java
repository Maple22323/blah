/*
 * Copyright (c) 2018 Charlie Waters
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.notes;

import java.net.URL;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.LinkBrowser;

@Slf4j
class NotesPanel extends PluginPanel
{
	private final JTextArea notesEditor = new JTextArea();

	void init(NotesConfig config)
	{
		// this may or may not qualify as a hack
		// but this lets the editor pane expand to fill the whole parent panel
		getParent().setLayout(new BorderLayout());
		getParent().add(this, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		notesEditor.setLineWrap(true);
		notesEditor.setWrapStyleWord(true);

		JPanel notesContainer = new JPanel();
		notesContainer.setLayout(new BorderLayout());
		notesContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		notesEditor.setOpaque(false);

		// load note text
		String data = config.notesData();
		notesEditor.setText(data);

		notesEditor.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					if (config.clickableLinks() && e.getClickCount() == 2)
					{
						String clickedWord = getWordUnderCursor();
						if (isValidUrl(clickedWord))
						{
							openUrlInBrowser(clickedWord);
						}
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}

			private boolean isValidUrl(String text)
			{
				try
				{
					URL url = new URL(text);
					url.toURI();
					return true;
				}
				catch (Exception ex)
				{
					return false;
				}
			}

			private void openUrlInBrowser(String url)
			{
				if (config.promptBeforeOpeningLinks())
				{
					if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(null,
							"Open the following link?: " + url, "Open Link?",
							JOptionPane.YES_NO_OPTION))
					{
						return;
					}
				}
				LinkBrowser.browse(url);
			}

			private String getWordUnderCursor() throws Exception
			{
				String text = notesEditor.getText();

				int startIdx = notesEditor.getCaretPosition() - 1;
				while (startIdx > 0 && !Character.isWhitespace(text.charAt(startIdx)))
				{
					startIdx--;
				}

				int endIdx = notesEditor.getCaretPosition();
				while (endIdx < text.length() && !Character.isWhitespace(text.charAt(endIdx)))
				{
					endIdx++;
				}

				return text.substring(startIdx, endIdx).trim();
			}
		});

		notesEditor.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{

			}

			@Override
			public void focusLost(FocusEvent e)
			{
				notesChanged(notesEditor.getDocument());
			}

			private void notesChanged(Document doc)
			{
				try
				{
					// get document text and save to config whenever editor is changed
					String data = doc.getText(0, doc.getLength());
					config.notesData(data);
				}
				catch (BadLocationException ex)
				{
					log.warn("Notes Document Bad Location: " + ex);
				}
			}
		});
		notesContainer.add(notesEditor, BorderLayout.CENTER);
		notesContainer.setBorder(new EmptyBorder(10, 10, 10, 10));

		add(notesContainer, BorderLayout.CENTER);
	}

	void setNotes(String data)
	{
		notesEditor.setText(data);
	}
}
